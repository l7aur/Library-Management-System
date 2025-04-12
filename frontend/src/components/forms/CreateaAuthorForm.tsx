import "./CreateForm.css"
import * as React from "react";
import {useState} from "react";
import {AuthorType} from "../../types/AuthorType.tsx";

interface Props {
    data: AuthorType;
    onClose: () => void;
    onSubmitCreate: (formData: AuthorType) => void;
    onSubmitUpdate: (formData: AuthorType) => void;
}

const CreateAuthorForm: React.FC<Props> = ({data, onClose, onSubmitCreate, onSubmitUpdate}) => {
    const [formData, setFormData] = useState({
        id: data.id,
        firstName: data.firstName,
        lastName: data.lastName,
        alias: data.alias,
        nationality: data.nationality,
        books: data.books
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        setFormData({...formData, [e.target.name]: e.target.value});
    };

    const handleSubmit = (e: React.FormEvent): void => {
        e.preventDefault();
        if (formData.id === "") {
            onSubmitCreate(formData);
        } else {
            onSubmitUpdate(formData);
        }
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
                type="number"
                name="alias"
                placeholder="Alias (optional)"
                value={formData.alias}
                onChange={handleChange}
                required
                autoComplete="username"
            />
            <input
                className="input_container"
                type="number"
                name="nationality"
                placeholder="Nationality"
                value={formData.nationality}
                onChange={handleChange}
                required
                autoComplete="username"
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

export default CreateAuthorForm;