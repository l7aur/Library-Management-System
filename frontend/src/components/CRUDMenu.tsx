import * as React from "react";
import "./CRUDMenu.css"

interface Props {
    err: string[];
    oks: string[];
    onCreate: () => void;
    onRead: () => void;
    onUpdate: () => void;
    onDelete: () => void;
}

export const CRUDMenu: React.FC<Props> = ({ err, oks, onCreate, onRead, onUpdate, onDelete }) => {
    return (
        <div>
            <div className="err_crud">
                {Array.isArray(err) &&
                    err.map((item, index) => (
                        <p key={index}>{item}</p>
                    ))
                }
            </div>
            <div className="oks_crud">
                {Array.isArray(oks) &&
                    oks.map((item, index) => (
                        <p key={index}>{item}</p>
                    ))
                }
            </div>
            <div className="crud_menu_container">
                <button onClick={onCreate}>Create</button>
                <button onClick={onRead}>Read</button>
                <button onClick={onUpdate}>Update</button>
                <button onClick={onDelete}>Delete</button>
            </div>
        </div>
    );
};