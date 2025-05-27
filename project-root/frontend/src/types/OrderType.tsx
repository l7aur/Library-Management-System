export interface OrderType {
    username: string,
    orderNumber: number,
    orderDate: number,
    bookId: string,
    price: number,
    quantity: number,
}

export interface Item {
    title: string,
    quantity: number,
    price: number
}

export interface FullOrder {
    items: Item[]
    date: string
}

export interface Order {
    orderNumber: number,
    fullOrder: FullOrder;
}