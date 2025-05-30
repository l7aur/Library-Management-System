export default interface BookTypeDTO {
    id: string;
    isbn: string;
    publishYear: number;
    title: string;
    publisherId: string;
    authorIds: string[];
    price: number;
    stock: number;
}