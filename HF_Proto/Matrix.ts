class Matrix {
	constructor(rows: number = 1, cols: number = 1) {
		let data = new Array<Array<number>>(rows).map(() => new Array<number>(cols).map(() => 0))

		console.log("Hey: ", data);

		return { ...this, ...data }
	}

	get rows(): number {
		return 1
	}

	get cols(): number {
		return this[0].length
	}
/* 
	get determinant(): number {
		return 0
	}

	get eigenVectors(): Array<Matrix> {
		return [new Matrix()]
	}

	get transpose(): Matrix {
		return new Matrix()
	}

	get conjugate(): Matrix {
		return new Matrix()
	}

	get hermitian(): Matrix {
		return new Matrix()
	}

	scalarProduct(n: number): Matrix {
		let newMatrix = new Matrix(this.rows, this.cols)
		for(let i = 0; i < this.rows; i++) {
			for(let j = 0; j < this.cols; j++) {
				newMatrix
			}
		}
	}
	matrixProduct(m: Matrix): Matrix {}
	tensorProduct(m: Matrix): Matrix {}
 */

}