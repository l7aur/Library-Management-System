import "./CreateForm.css"
import * as React from "react";
import {useState} from "react";

interface Props {
    onClose: () => void;
    onSubmit: (formData: { username: string; password: string }) => void;
}

const CreateAppUserForm: React.FC<Props> = ({onClose, onSubmit}) => {
    const [formData, setFormData] = useState({ username: "", password: "" });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        console.log("submit", formData);
        onSubmit(formData);
        onClose();
    };

    return (
        <div className="create_form_container">
            <div>
                <input
                    type="text"
                    name="username"
                    placeholder="Username"
                    value={formData.username}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="password"
                    placeholder="Password"
                    value={formData.password}
                    onChange={handleChange}
                    required
                />
            </div>
            <div>
                <button onClick={onClose}>Cancel</button>
                <button onClick={handleSubmit}>Submit</button>
            </div>
        </div>
    );
};

export default CreateAppUserForm;