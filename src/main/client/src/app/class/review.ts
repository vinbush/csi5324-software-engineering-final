export class Review {
    constructor(
        public id: number,
        public rating: number,
        public textBody: string,
        public reviewerName: string,
    ) {}
}
