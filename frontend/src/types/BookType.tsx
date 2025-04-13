import BookTypeDTO from "./BookTypeDTO";
import PublisherType from "./PublisherType.tsx";
import {AuthorType} from "./AuthorType.tsx";

export interface BookType {
    id: string;
    isbn: string;
    publishYear: number;
    title: string;
    publisher: PublisherType;
    authors: AuthorType[];
    price: number;
    stock: number;

    toDTO(): BookTypeDTO;
}

export class BookTypeImpl implements BookType {
    id: string;
    isbn: string;
    publishYear: number;
    title: string;
    publisher: PublisherType;
    authors: AuthorType[];
    price: number;
    stock: number;

    constructor(
        id: string,
        isbn: string,
        publishYear: number,
        title: string,
        publisherId: PublisherType,
        authorIds: AuthorType[],
        price: number,
        stock: number
    ) {
        this.id = id;
        this.isbn = isbn;
        this.publishYear = publishYear;
        this.title = title;
        this.publisher = publisherId;
        this.authors = authorIds;
        this.price = price;
        this.stock = stock;
    }

    toDTO(): BookTypeDTO {
        return {
            id: this.id,
            isbn: this.isbn,
            publishYear: this.publishYear,
            title: this.title,
            publisherId: this.publisher.id.toString(),
            authorIds: this.authors.map(a => a.id.toString()),
            price: this.price,
            stock: this.stock,
        };
    }
}