import "./CreateForm.css"
import * as React from "react";
import {useState} from "react";
import PublisherType from "../../types/PublisherType.tsx";

interface Props {
    data: PublisherType;
    onClose: () => void;
    onSubmitCreate: (formData: PublisherType) => void;
    onSubmitUpdate: (formData: PublisherType) => void;
}

const CreatePublisherForm: React.FC<Props> = ({data, onClose, onSubmitCreate, onSubmitUpdate}) => {
    const [formData, setFormData] = useState({
        id: data.id,
        name: data.name,
        location: data.location,
        foundingYear: data.foundingYear,
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
                name="name"
                placeholder="Name"
                value={formData.name}
                onChange={handleChange}
                required
            />
            <input
                className="input_container"
                type="text"
                name="location"
                placeholder="Location"
                value={formData.location}
                onChange={handleChange}
                required
            />
            <input
                className="input_container"
                type="number"
                name="foundingYear"
                placeholder="Founding Year"
                value={formData.foundingYear}
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

export default CreatePublisherForm;