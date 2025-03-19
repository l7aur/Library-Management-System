export interface BookT {
    id: string;
    isbn: string;
    publishYear: number;
    title: string;
    publisher: PublisherRed;
    authors: AuthorRed[];
    price: number;
    stock: number;
}

export interface PublisherRed {
    id: string;
    name: string;
    location: string;
    foundingYear: number;
}

export interface AuthorRed {
    id: string;
}