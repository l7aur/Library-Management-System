import {useState} from "react";
import useFindAllAuthors from "../hooks/useFindAllAuthors.tsx";
import {AuthorType} from "../types/AuthorType.tsx";
import AuthorsTable from "../components/tables/AuthorsTable.tsx";
import {add, del, update} from "../services/AuthorService.ts";
import {CRUDMenu} from "../components/CRUDMenu.tsx";
import CreateAuthorForm from "../components/forms/CreateaAuthorForm.tsx";

const AuthorsPage = () => {
    const {fData, setFData, fLoading, isFError, refetch} = useFindAllAuthors();
    const [clearSelection, setClearSelection] = useState<boolean>(false);
    const [selectedAuthors, setSelectedAuthors] = useState<AuthorType[]>([]);
    const [isCreateFormOpen, setIsCreateFormOpen] = useState(false);
    const [error, setError] = useState<string[]>([]);
    const [okays, setOkays] = useState<string[]>([]);
    const [formFillData, setFormFillData] = useState<AuthorType>({
        id: "",
        firstName: "",
        lastName: "",
        alias: "",
        nationality: "",
        books: []
    });

    const isValidAuthor = (author: AuthorType) => {
        return Boolean(author.id)
        && Boolean(author.firstName)
        && Boolean(author.lastName)
        && Boolean(author.nationality);
    }
    const handleCreate = (newAuthor: AuthorType) => {
        setError([]);
        setOkays([]);
        add(newAuthor)
            .then((response : AuthorType) => ({
                id: response.id,
                firstName: response.firstName,
                lastName: response.lastName,
                alias: response.alias,
                nationality: response.nationality,
                books: response.books
            }))
            .then((formattedAuthor) => {
                if (isValidAuthor(formattedAuthor)) {
                    setFData((prevFData) =>
                        prevFData.length > 0
                            ? [...prevFData, formattedAuthor]
                            : [formattedAuthor]
                    );
                } else {
                    const err: string[] =
                        [formattedAuthor.id
                            , formattedAuthor.firstName
                            , formattedAuthor.lastName
                            , formattedAuthor.nationality
                            , formattedAuthor.alias
                            , formattedAuthor.books.toString()
                        ];
                    throw new Error(err.filter((x) => x != undefined).join("\n"));
                }
            })
            .catch((error) => {
                const err: string[] = error.message.split("\n").filter((x: string) => x !== "");
                setError(err);
            })
    }
    const handleRead = () => {
        refetch().then(r => console.log(r));
    }
    const handleUpdate = (newAuthor: AuthorType) => {
        setError([]);
        setOkays([]);
        update(newAuthor)
            .then((response : AuthorType) => ({
                id: response.id,
                firstName: response.firstName,
                lastName: response.lastName,
                alias: response.alias,
                nationality: response.nationality,
                books: response.books
            }))
            .then((formattedAuthor) => {
                if(isValidAuthor(formattedAuthor)) {
                    setFData((prevFData) =>
                        prevFData.map((entry) =>
                            entry.id === formattedAuthor.id ? {...entry, ...formattedAuthor} : entry
                        ));
                } else {
                    const err: string[] =
                        [formattedAuthor.id
                            , formattedAuthor.firstName
                            , formattedAuthor.lastName
                            , formattedAuthor.alias
                            , formattedAuthor.nationality
                            , formattedAuthor.books.toString()
                        ];
                    throw new Error(err.filter((x) => x != undefined).join("\n"));
                }
            })
            .catch((error) => {
                const err: string[] = error.message.split("\n").filter((x: string) => x !== "");
                setError(err);
            })
    }
    const handleDelete = () => {
        setError([]);
        setOkays([]);
        const ids = selectedAuthors.map((entry: AuthorType) => entry.id);
        del(ids)
            .then((r) => {
                if(200 === r) {
                    setOkays(["Success!"]);
                }
                else {
                    setError(["Error!"]);
                }
            })
            .finally(() => {
                setClearSelection(true);
                setSelectedAuthors([]);
                setFData((prevFData) => prevFData.filter((item) => !ids.includes(item.id)));
            });
    }
    const onRowSelect = (state: { selectedRows: AuthorType[] }) => {
        setSelectedAuthors(state.selectedRows);
    };

    return (
        <div className="page-container">
            <AuthorsTable
                data={fData}
                loading={fLoading}
                isError={isFError}
                onRowSelect={onRowSelect}
                clearSelection={clearSelection}
            />
            {!isCreateFormOpen &&
                (<CRUDMenu
                        err={error}
                        oks={okays}
                        onCreate={() => setIsCreateFormOpen(true)}
                        onRead={() => {
                            setError([]);
                            setOkays([]);
                            setIsCreateFormOpen(false);
                            if(selectedAuthors && selectedAuthors.length > 0) {
                                setError(["Cannot update the table if publishers are selected!"]);
                                setSelectedAuthors([]);
                            } else {
                                handleRead();
                            }
                        }}
                        onUpdate={() => {
                            setError([]);
                            setOkays([]);
                            if(selectedAuthors && selectedAuthors.length === 1) {
                                setIsCreateFormOpen(true);
                                setFormFillData(selectedAuthors[0]);
                            }
                            else {
                                setError(["Can update just one publisher at a time!"]);
                            }
                        }}
                        onDelete={handleDelete}/>
                )}
            {isCreateFormOpen && (
                <CreateAuthorForm
                    data={formFillData}
                    onClose={() => {
                        setIsCreateFormOpen(false);
                        setFormFillData({id: "", firstName: "", lastName: "", alias: "", nationality: "", books: []})
                    }}
                    onSubmitCreate={(newAuthor) => handleCreate(newAuthor)}
                    onSubmitUpdate={(newAuthor) => handleUpdate(newAuthor)}
                />
            )}
        </div>
    )
}

export default AuthorsPage;