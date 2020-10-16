import java.lang.Math;

/*class Complex {
	public static final double TAU = Math.PI * 2;
	double re, im, arg, r;

	Complex() {
		this.r = 0;
		this.arg = 0;
	}
	public Complex(double r, double arg) {
		this.r = r;
		this.arg = arg;
	}


	public double getR() {
		return r;
	}
	public void setR(double r) {
		this.r = r;
		this.re = r * Math.cos(arg);
		this.im = r * Math.sin(arg);
	}

	public double getArg() {
		return arg;
	}
	public void setArg(double arg) {
		arg = arg % Complex.TAU;
		this.arg = arg;
		this.re = r * Math.cos(arg);
		this.im = r * Math.sin(arg);
	}


	
	public Complex mul(double s) {
		return new Complex(this.re * s, this.im * s);
	}
	public Complex mul(final Complex z) {
		return new Complex(
			this.re * z.re - this.im * z.im,
			this.re * z.im + this.im * z.re
		);
	}

	public Complex div(double s) {
		return new Complex(this.re / s, this.im / s);
	}
	public Complex div(final Complex z) {
		return this.mul(z.reciprocal());
	}

	public Complex pow(double d) {
		Complex ret = new Complex();
		ret.r = Math.pow(this.r, d);
		ret.setArg(this.arg * d % Complex.TAU);
		return ret;
	}
	public Complex pow(final Complex z) {
		System.out.println("ln("+this+")=" + this.ln());
		Complex ePow = z.mul(this.ln());
		System.out.println("ln:" + ePow);
		Complex ret = new Complex();
		ret.r = Math.pow(Math.E, ePow.re);
		ret.setArg(ePow.im);
		return ret;
	}

	public Complex ln() {
		return new Complex(Math.log(this.r), this.arg);
	}


	public Complex conjugate() {
		return new Complex(this.re, -this.im);
	}

	public Complex reciprocal() {
		double scale = this.sumOfSquares();
		return new Complex(this.re / scale, -this.im / scale);
	}



	double abs() {
		return Math.sqrt(this.sumOfSquares());
	}

	double sumOfSquares() {
		return this.re * this.re + this.im * this.im;
	}


	public boolean equals(double d) {
		return this.re == d && this.im == 0;
	}
	public boolean equals(Complex z) {
		if(z == null) return false;
		return this.re == z.re && this.im == z.im;
	}

	/**		x,y,r ⋴ R & i=(-1)^(1/2) φ ⋴ [0,2π) φ may contain π
	/^(
		?:([\d]+(?:\.[\d]+)?)\+([\d]+(?:\.[\d]+)?)i|							algebraic					x+yi
		([\d]+(?:\.[\d]+)?)?e\^([\d]+(?:\.[\d]+)?[π]?)i|					exponential				re^φi
		([\d]+(?:\.[\d]+)?)i|																			Im of algebraic		yi
		([\d]+(?:\.[\d]+)?)|																			Re of algebraic		x
	)$/
	/

	public String toString() {
		return this.r+"e^i*"+this.arg;
	}
}
 */

public class Complex {
	public static final double TAU = Math.PI * 2;
	/**
	 * @apiNote |Z|
	 */
	private double r;
	/**
	 * @apiNote times 2π = τ
	 */
	private double arg;

	public Complex(double arg) { this(1.0, arg); }
	public Complex(double r, double arg) {
		this.r = r;
		this.arg = arg;
	}

	/**
	 * @apiNote |Z|
	 */
	public double getR() { return this.r; }
	/**
	 * @apiNote times 2π
	 */
	public double getArg() { return this.arg; }

	public Complex mul(double s) {
		return new Complex(this.r * s, this.arg);
	}

	public Complex mul(final Complex z) {
		return new Complex(this.r * z.r, this.arg + z.arg);
	}


}
