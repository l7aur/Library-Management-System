import {useState} from "react";
import BookType from "../types/BookType.tsx";
import BooksTable from "../components/tables/BooksTable.tsx";
import useFindAllBooks from "../hooks/useFindAllBooks.tsx";
import {add, del, findFiltered, update} from "../services/BookService.ts";
import {CRUDMenu} from "../components/CRUDMenu.tsx";
import BookSearchBar from "../components/BookSearchBar.tsx";
import CreateBookForm from "../components/forms/CreateBookForm.tsx";

const BooksPage = () => {
    const {fData, setFData, fLoading, isFError, refetch} = useFindAllBooks();
    const [clearSelection, setClearSelection] = useState<boolean>(false);
    const [selectedBooks, setSelectedBooks] = useState<BookType[]>([]);
    const [isCreateFormOpen, setIsCreateFormOpen] = useState(false);
    const [error, setError] = useState<string[]>([]);
    const [okays, setOkays] = useState<string[]>([]);
    const [formFillData, setFormFillData] = useState<BookType>({
        id: "",
        isbn: "",
        publishYear: 0,
        title: "",
        publisherId: "",
        authorIds: [],
        price: -1,
        stock: -1,
    });

    const isValidBook = (book: BookType): boolean => {
        return Boolean(book.id)
            && Boolean(book.isbn)
            && Boolean(book.title)
            && Boolean(book.publishYear != 0)
            && Boolean(book.publisherId)
            && Boolean(book.authorIds)
            && (book.stock >= 0)
            && Boolean(book.price > 0);
    };
    const handleCreate = (newBook: BookType) => {
        setError([]);
        setOkays([]);
        add(newBook)
            .then((response: BookType) => ({
                id: response.id,
                isbn: response.isbn,
                publishYear: response.publishYear,
                title: response.title,
                publisherId: response.publisherId,
                authorIds: response.authorIds,
                stock: response.stock,
                price: response.price
            }))
            .then((formattedResponse) => {
                if (isValidBook(formattedResponse)) {
                    setFData((prevFData) =>
                        prevFData.length > 0
                            ? [...prevFData, formattedResponse]
                            : [formattedResponse]
                    );
                } else {
                    const err: string[] =
                        [formattedResponse.id
                            , formattedResponse.isbn
                            , formattedResponse.publishYear.toString()
                            , formattedResponse.title
                            , formattedResponse.publisherId
                            , formattedResponse.authorIds.join("\n")
                            , formattedResponse.stock.toString()
                            , formattedResponse.price.toString()
                        ];
                    throw new Error(err.filter((x) => x != undefined).join("\n"));
                }
            })
            .catch((error) => {
                const err: string[] = error.message.split("\n").filter((x: string) => x !== "");
                setError(err);
            });
    };
    const handleRead = () => {
        refetch().then(r => console.log(r));
    };
    const handleFilteredSearch = (title: string, stock: number) => {
        setFData([]);
        setError([]);
        findFiltered(title, stock)
            .then(response => setFData(response))
            .catch((error) => {setError([error])})
    };
    const handleUpdate = (newBook: BookType) => {
        setError([]);
        setOkays([]);
        update(newBook)
            .then((response: BookType) => ({
                id: response.id,
                isbn: response.isbn,
                publishYear: response.publishYear,
                title: response.title,
                publisherId: response.publisherId,
                authorIds: response.authorIds,
                stock: response.stock,
                price: response.price
            }))
            .then((formattedResponse) => {
                if (isValidBook(formattedResponse)) {
                    setFData((prevFData) =>
                        prevFData.map((entry) =>
                            entry.id === formattedResponse.id ? {...entry, ...formattedResponse} : entry
                        ));
                } else {
                    const err: string[] =
                        [formattedResponse.id
                            , formattedResponse.isbn
                            , formattedResponse.publishYear.toString()
                            , formattedResponse.title
                            , formattedResponse.publisherId
                            , formattedResponse.authorIds.join("\n")
                            , formattedResponse.stock.toString()
                            , formattedResponse.price.toString()
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
        const ids = selectedBooks.map((item) => item.id);
        del(ids)
            .then((r) => {
                if (200 === r) {
                    setOkays(["Success!"]);
                } else {
                    setError(["Error!"]);
                }
            })
            .finally(() => {
                setClearSelection(true);
                setSelectedBooks([]);
                setFData((prevFData) => prevFData.filter((item) => !ids.includes(item.id)));
            });
    }
    const onRowSelect = (state: { selectedRows: BookType[] }) => {
        setSelectedBooks(state.selectedRows);
    };

    return (
        <div className="page_container">
            <BookSearchBar
                onSearch={handleFilteredSearch}
            />
            <BooksTable
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
                            if (selectedBooks && selectedBooks.length > 0) {
                                setError(["Cannot update the table if books are selected!"]);
                                setSelectedBooks([]);
                            } else {
                                handleRead();
                            }
                        }}
                        onUpdate={() => {
                            setError([]);
                            setOkays([]);
                            if (selectedBooks && selectedBooks.length === 1) {
                                setIsCreateFormOpen(true);
                                setFormFillData(selectedBooks[0]);
                            } else {
                                setError(["Can update just one user at a time!"]);
                            }
                        }}
                        onDelete={handleDelete}
                    />
                )}
            {isCreateFormOpen && (
                <CreateBookForm
                    data={formFillData}
                    onClose={() => {
                        setIsCreateFormOpen(false);
                        setFormFillData({id: "", isbn: "", publishYear: 0, title: "", publisherId: "", authorIds: [], stock: 0, price: 0});
                    }}
                    onSubmitCreate={(newUserData) => handleCreate(newUserData)}
                    onSubmitUpdate={(newUserData) => handleUpdate(newUserData)}
                />
            )}
        </div>
    )
}

export default BooksPage;