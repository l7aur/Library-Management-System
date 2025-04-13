import "./CreateForm.css"
import * as React from "react";
import {useEffect, useState} from "react";
import {AUTHORS_GET_ALL_ENDPOINT, PUBLISHERS_GET_ALL_ENDPOINT} from "../../constants/API.ts";
import PublisherType from "../../types/PublisherType.tsx";
import {AuthorType} from "../../types/AuthorType.tsx";
import Select, {MultiValue, Theme} from "react-select";
import BookTypeDTO from "../../types/BookTypeDTO.tsx";

interface Props {
    data: BookTypeDTO;
    onClose: () => void;
    onSubmitCreate: (formData: BookTypeDTO) => void;
    onSubmitUpdate: (formData: BookTypeDTO) => void;
}

const CreateBookForm: React.FC<Props> = ({data, onClose, onSubmitCreate, onSubmitUpdate}) => {
    const [formData, setFormData] = useState({
        id: data.id,
        isbn: data.isbn,
        publishYear: data.publishYear,
        title: data.title,
        publisherId: data.publisherId,
        authorIds: data.authorIds,
        stock: data.stock,
        price: data.price
    });
    const [publishers, setPublishers] = useState<PublisherType[]>([]);
    const [pLoading, setPLoading] = useState(true);
    const [pError, setPError] = useState<string>("");
    const [authors, setAuthors] = useState<AuthorType[]>([]);
    const [aLoading, setALoading] = useState(true);
    const [aError, setAError] = useState<string>("");

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

    useEffect(() => {
        const fetchPublishers = async () => {
            const response = await fetch(PUBLISHERS_GET_ALL_ENDPOINT);
            if (!response.ok) {
                setPError(`HTTP error! status: ${response.status}`)
                setPLoading(false);
            } else {
                const data = await response.json();
                setPublishers(data);
                setPLoading(false);
            }
        };

        fetchPublishers()
            .catch(error => setPError(error));
    }, []);

    useEffect(() => {
        const fetchAuthors = async () => {
            const response = await fetch(AUTHORS_GET_ALL_ENDPOINT); // Replace with your actual API endpoint
            if (!response.ok) {
                setAError(`HTTP error! status: ${response.status}`);
                setALoading(false);
            } else {
                const data = await response.json();
                setAuthors(data);
                setALoading(false);
            }
        };

        fetchAuthors()
            .catch(error => setAError(error));
    }, []);

    const handleAuthorSelectChange = (selectedOptions: MultiValue<{ value: string; label: string }>) => {
        const authorIds = selectedOptions ? selectedOptions.map((option) => option.value) : [];
        setFormData({ ...formData, authorIds: authorIds });
    };

    const darkTheme = (theme: Theme) => ({
        ...theme,
        colors: {
            ...theme.colors,
            primary: '#333',       // Main interactive color
            primary25: '#444',     // Hover/focus background
            primary50: '#555',
            primary75: '#666',
            neutral0: '#222',       // Background color
            neutral5: '#333',
            neutral10: '#444',
            neutral20: '#555',
            neutral30: '#666',
            neutral40: '#777',
            neutral50: '#888',
            neutral60: '#999',
            neutral70: '#aaa',
            neutral80: '#bbb',
            neutral90: '#ccc',
        },
    });

    return (
        <form className="create_form_container">
            <input
                className="input_container"
                type="text"
                name="isbn"
                placeholder="ISBN"
                value={formData.isbn}
                onChange={handleChange}
                required
            />
            <input
                className="input_container"
                type="number"
                name="publishYear"
                placeholder="Publication Year"
                value={formData.publishYear}
                onChange={handleChange}
                required
            />
            <input
                className="input_container"
                type="text"
                name="title"
                placeholder="Title"
                value={formData.title}
                onChange={handleChange}
                required
            />
            <select
                className="select_container"
                name="publisherId"
                value={formData.publisherId}
                onChange={handleChange}
                id="publisherId"
            >
                <option value="" disabled>Select a publisher</option>
                {pLoading && <option disabled>Loading publishers...</option>}
                {pError && <option disabled>Error loading publishers.</option>}
                {!pLoading && !pError && publishers.map(publisher => (
                    <option key={publisher.id} value={publisher.id}>
                        {publisher.name}
                    </option>
                ))}
            </select>
            {aLoading && <div>Loading authors...</div>}
            {aError && <div>Error loading authors.</div>}
            {!aLoading && !aError && (
                <Select
                    defaultValue={
                        authors
                            .filter(a => data.authorIds.includes(a.id))
                            .map(author => ({
                                value: author.id.toString(),
                                label: author.firstName+ " "+ author.lastName
                            }))}
                    isMulti
                    name="authors"
                    options={authors.map(author => ({
                        value: author.id.toString(),
                        label: author.firstName + " " + author.lastName,
                    }))}
                    className="select_container"
                    classNamePrefix="select"
                    theme={darkTheme}
                    onChange={handleAuthorSelectChange}
                />
            )}
            <input
                className="input_container"
                type="number"
                name="stock"
                placeholder="Stock"
                value={formData.stock}
                onChange={handleChange}
                required
            />
            <input
                className="input_container"
                type="number"
                name="price"
                placeholder="Price"
                value={formData.price}
                onChange={handleChange}
                required
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

export default CreateBookForm;