import CreateAppUserForm from "../components/forms/CreateAppUserForm.tsx";
import {AppUserType} from "../types/AppUserType.tsx";
import AppUserFormDataType from "../types/FormDataType.tsx";
import {add} from "../services/AppUserService.ts";
import {useState} from "react";
import {HOME_PATH} from "../constants/Paths.ts";
import {useNavigate} from "react-router-dom";

const RegisterPage = () => {
    const [error, setError] = useState<string[]>([]);
    const [okays, setOkays] = useState<string[]>([]);
    const [formFillData] = useState<AppUserType>({
        id: "",
        username: "",
        password: "",
        role: "",
        email: "",
        firstName: "",
        lastName: ""
    });

    const isValidUser = (user: AppUserType): boolean => {
        return Boolean(user.username)
            && Boolean(user.firstName)
            && Boolean(user.lastName)
            && Boolean(user.role)
            && Boolean(user.password)
            && Boolean(user.id)
    };
    const navigate = useNavigate();

    const handleClose = () => {
        setError([]);
        setOkays([]);
        navigate(HOME_PATH);
    }

    const handleCreate = (newUser: AppUserFormDataType) => {
        setError([]);
        setOkays([]);
        if (newUser.password !== newUser.confirmation) {
            setError(["Password and its confirmation mismatch!"]);
            return;
        }
        add(newUser)
            .then((response: AppUserType) => ( {
                id: response.id,
                role: response.role,
                firstName: response.firstName,
                lastName: response.lastName,
                username: response.username,
                password: response.password,
                email: response.email,
                message: response.message
            }))
            .then((formattedResponse) => {
                if (isValidUser(formattedResponse)) {
                    setOkays(["Success!"]);
                } else {
                    const err: string[] =
                        [formattedResponse.id
                            , formattedResponse.username
                            , formattedResponse.password
                            , formattedResponse.role
                            , formattedResponse.firstName
                            , formattedResponse.email
                            , formattedResponse.lastName
                        ];
                    if(formattedResponse.message != undefined) {
                        err.push(formattedResponse.message);
                    }
                    throw new Error(err.filter((x) => x != undefined).join("\n"));
                }
            })
            .catch((error) => {
                const err: string[] = error.message.split("\n").filter((x: string) => x !== "");
                setError(err);
            });
    };

    return (
        <div className="register_page">
            <h1>Welcome to our community!</h1>
            <h2>Please register to be able to access our services!</h2>
            {error.length > 0 && (
                <div>
                    <h3>Errors:</h3>
                    <ul>
                        {error.map((err, index) => (
                            <li key={index} className="error-text">{err}</li>
                        ))}
                    </ul>
                </div>
            )}
            {okays.length > 0 && (
                <div>
                    <h3>Success:</h3>
                    <ul>
                        {okays.map((msg, index) => (
                            <li key={index} className="info-text">{msg}</li>
                        ))}
                    </ul>
                </div>
            )}
            <CreateAppUserForm
                data={formFillData}
                onClose={handleClose}
                onSubmitCreate={(newUserData) => handleCreate(newUserData)}
                onSubmitUpdate={(newUserData) => handleCreate(newUserData)}
            />
        </div>
    )
}

export default RegisterPage;