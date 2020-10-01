class Matrix {
    constructor(rows = 1, cols = 1) {
        let data = new Array(rows).map(() => new Array(cols).map(() => 0));
        console.log("Hey: ", data);
        return { ...this, ...data };
    }
    get rows() {
        return 1;
    }
    get cols() {
        return this[0].length;
    }
}
