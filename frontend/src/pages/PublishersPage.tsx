import {useState} from "react";
import PublisherType from "../types/PublisherType.tsx";
import PublishersTable from "../components/tables/PublishersTable.tsx";
import useFindAllPublishers from "../hooks/useFindAllPublishers.tsx";
import {add, del, update} from "../services/PublisherService.ts";
import {CRUDMenu} from "../components/CRUDMenu.tsx";
import CreatePublisherForm from "../components/forms/CreatePublisherForm.tsx";
import {useAuth} from "../config/GlobalState.tsx";

const PublishersPage = () => {
    const {fData, setFData, fLoading, isFError, refetch} = useFindAllPublishers();
    const [clearSelection, setClearSelection] = useState<boolean>(false);
    const [selectedPublishers, setSelectedPublishers] = useState<PublisherType[]>([]);
    const [isCreateFormOpen, setIsCreateFormOpen] = useState(false);
    const [error, setError] = useState<string[]>([]);
    const [okays, setOkays] = useState<string[]>([]);
    const [formFillData, setFormFillData] = useState<PublisherType>({
        id: "",
        name: "",
        location: "",
        foundingYear: 0,
        books: []
    });
    const { token } = useAuth();

    const isValidPublisher = (publisher: PublisherType) : boolean => {
        return Boolean(publisher.id)
            && Boolean(publisher.name)
            && Boolean(publisher.foundingYear)
            && Boolean(publisher.location);
    }
    const handleCreate = (newPublisher: PublisherType) => {
        setError([]);
        setOkays([]);
        add(newPublisher, token)
            .then((response : PublisherType) => ({
                id: response.id,
                name: response.name,
                location: response.location,
                foundingYear: response.foundingYear,
                books: response.books
            }))
            .then((formattedPublisher) => {
                if (isValidPublisher(formattedPublisher)) {
                    setFData((prevFData) =>
                        prevFData.length > 0
                            ? [...prevFData, formattedPublisher]
                            : [formattedPublisher]
                    );
                } else {
                    const err: string[] =
                        [formattedPublisher.id
                        , formattedPublisher.name
                        , formattedPublisher.location
                        , formattedPublisher.foundingYear.toString()
                        , formattedPublisher.books.toString()
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
        refetch();
    }
    const handleUpdate = (newPublisher: PublisherType) => {
        setError([]);
        setOkays([]);
        update(newPublisher, token)
            .then((response : PublisherType) => ({
                id: response.id,
                name: response.name,
                location: response.location,
                foundingYear: response.foundingYear,
                books: response.books
            }))
            .then((formattedPublisher) => {
                if(isValidPublisher(formattedPublisher)) {
                    setFData((prevFData) =>
                        prevFData.map((entry) =>
                            entry.id === formattedPublisher.id ? {...entry, ...formattedPublisher} : entry
                        ));
                } else {
                    const err: string[] =
                        [formattedPublisher.id
                            , formattedPublisher.name
                            , formattedPublisher.location
                            , formattedPublisher.foundingYear.toString()
                            , formattedPublisher.books.toString()
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
        const ids = selectedPublishers.map((entry: PublisherType) => entry.id);
        del(ids, token)
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
                setSelectedPublishers([]);
                setFData((prevFData) => prevFData.filter((item) => !ids.includes(item.id)));
            });
    }
    const onRowSelect = (state: { selectedRows: PublisherType[] }) => {
        setSelectedPublishers(state.selectedRows);
    };

    return (
        <div className="page-container">
            <PublishersTable
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
                        if(selectedPublishers && selectedPublishers.length > 0) {
                            setError(["Cannot update the table if publishers are selected!"]);
                            setSelectedPublishers([]);
                        } else {
                            handleRead();
                        }
                    }}
                    onUpdate={() => {
                        setError([]);
                        setOkays([]);
                        if(selectedPublishers && selectedPublishers.length === 1) {
                            setIsCreateFormOpen(true);
                            setFormFillData(selectedPublishers[0]);
                        }
                        else {
                            setError(["Can update just one publisher at a time!"]);
                        }
                    }}
                    onDelete={handleDelete}/>
                )}
            {isCreateFormOpen && (
                <CreatePublisherForm
                    data={formFillData}
                    onClose={() => {
                        setIsCreateFormOpen(false);
                        setFormFillData({id: "", name: "", location: "", foundingYear: 0, books: [""]})
                    }}
                    onSubmitCreate={(newPublisher) => handleCreate(newPublisher)}
                    onSubmitUpdate={(newPublisher) => handleUpdate(newPublisher)}
                />
            )}
        </div>
    )
}

export default PublishersPage;