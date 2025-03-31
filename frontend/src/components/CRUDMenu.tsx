import * as React from "react";
import "./CRUDMenu.css"

interface Props {
    onCreate: () => void;
    onRead: () => void;
    onUpdate: () => void;
    onDelete: () => void;
}

export const CRUDMenu: React.FC<Props> = ({ onCreate, onRead, onUpdate, onDelete }) => {
    return (
        <div className="crud_menu_container">
            <button onClick={onCreate}>Create</button>
            <button onClick={onRead}>Read</button>
            <button onClick={onUpdate}>Update</button>
            <button onClick={onDelete}>Delete</button>
        </div>
    );
};