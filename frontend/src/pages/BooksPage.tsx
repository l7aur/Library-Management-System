import {useState} from "react";
import {BookType, BookTypeImpl} from "../types/BookType.tsx";
import BooksTable from "../components/tables/BooksTable.tsx";
import useFindAllBooks from "../hooks/useFindAllBooks.tsx";
import {add, del, findFiltered, update} from "../services/BookService.ts";
import {CRUDMenu} from "../components/CRUDMenu.tsx";
import BookSearchBar from "../components/BookSearchBar.tsx";
import CreateBookForm from "../components/forms/CreateBookForm.tsx";
import BookTypeDTO from "../types/BookTypeDTO.tsx";
import {useAuth} from "../config/GlobalState.tsx";

const BooksPage = () => {
    const {fData, setFData, fLoading, isFError, refetch} = useFindAllBooks();
    const [clearSelection, setClearSelection] = useState<boolean>(false);
    const [selectedBooks, setSelectedBooks] = useState<BookTypeImpl[]>([]);
    const [isCreateFormOpen, setIsCreateFormOpen] = useState(false);
    const [error, setError] = useState<string[]>([]);
    const [okays, setOkays] = useState<string[]>([]);
    const [formFillData, setFormFillData] = useState<BookTypeDTO>({
        id: "",
        isbn: "",
        publishYear: 0,
        title: "",
        publisherId: "",
        authorIds: [],
        price: -1,
        stock: -1,
    });
    const { token } = useAuth();

    const isValidBook = (book: BookType): boolean => {
        return Boolean(book?.isbn)
            && Boolean(book?.title)
            && Boolean(book?.publishYear !== undefined && book.publishYear > 0)
            && Boolean(book?.publisher)
            && Boolean(book?.authors && book.authors.length > 0)
            && (book?.stock !== undefined && book.stock >= 0)
            && Boolean(book?.price !== undefined && book.price > 0);
    };
    const handleCreate = (newBook: BookTypeDTO) => {
        setError([]);
        setOkays([]);
        console.log(newBook);
        add(newBook, token)
            .then((response: BookType) => {
                return response;
            })
            .then((formattedResponse) => {
                console.log("formatted book: ", formattedResponse);
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
                            , formattedResponse.publisher.id
                            , formattedResponse.authors.join("\n")
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
        findFiltered(token, title, stock)
            .then(response => setFData(response))
            .catch((error) => {setError([error])})
    };
    const handleUpdate = (newBook: BookTypeDTO) => {
        setError([]);
        setOkays([]);
        update(newBook, token)
            .then((response: BookType) => {
                {
                    return response;
                }
            })
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
                            , formattedResponse.publisher.id
                            , formattedResponse.authors.join("\n")
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
        del(ids, token)
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
                                setFormFillData({
                                    id: selectedBooks[0].id,
                                    isbn: selectedBooks[0].isbn,
                                    publishYear: selectedBooks[0].publishYear,
                                    publisherId: selectedBooks[0].publisher.id,
                                    authorIds: selectedBooks[0].authors.map(a => a.id.toString()),
                                    title: selectedBooks[0].title,
                                    stock: selectedBooks[0].stock,
                                    price: selectedBooks[0].price
                                });
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