import "./CreateForm.css"
import * as React from "react";
import {useState} from "react";
import UserFormDataType from "../../types/UserFormDataType.tsx";

interface Props {
    onClose: () => void;
    onSubmit: (formData: UserFormDataType) => void;
}

const CreateAppUserForm: React.FC<Props> = ({onClose, onSubmit}) => {
    const [formData, setFormData] = useState({id: "", username: "", password: "", role: "", firstName: "", lastName: "", confirmation: ""});

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        setFormData({...formData, [e.target.name]: e.target.value});
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        console.log("submit", formData);
        onSubmit(formData);
        onClose();
    };

    return (
        <form className="create_form_container">
            <input
                className="input_container"
                type="text"
                name="firstName"
                placeholder="First Name"
                value={formData.firstName}
                onChange={handleChange}
                required
            />
            <input
                className="input_container"
                type="text"
                name="lastName"
                placeholder="Last Name"
                value={formData.lastName}
                onChange={handleChange}
                required
            />
            <input
                className="input_container"
                type="text"
                name="username"
                placeholder="Username"
                value={formData.username}
                onChange={handleChange}
                required
                autoComplete="username"
            />
            <select className="select_container" name="role" value={formData.role} onChange={handleChange}>
                <option value="" disabled={true}>Select a role</option>
                <option value="ADMIN">Admin</option>
                <option value="EMPLOYEE">Employee</option>
                <option value="CUSTOMER">Customer</option>
            </select>
            <input
                className="input_container"
                type="password"
                name="password"
                placeholder="Password"
                value={formData.password}
                onChange={handleChange}
                required
                autoComplete="new-password"
            />
            <input
                className="input_container"
                type="password"
                name="confirmation"
                placeholder="Password confirmation"
                value={formData.confirmation}
                onChange={handleChange}
                required
                autoComplete="new-password"
            />

            <div>
                <button className="button_container" type="button" onClick={onClose}>
                    Cancel
                </button>
                <button className="button_container" type="submit" onClick={handleSubmit}>
                    Submit
                </button>
            </div>
        </form>
    );
}

export default CreateAppUserForm;