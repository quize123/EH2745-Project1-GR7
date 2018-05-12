package Project;

//*******************************************************************************************

//This class was written to deal with complex numbers mathmatical operations which are in case
// of this project:
// 1- CMPLX(Real,Img) which defines complex number as an object
// 1- Addition(Real,Img) which add another complex number object 
// 2- Subtraction(Real,Img) which subtract another complex number object from X
// 3- Real(Object) & IMG(Object) which return real and imaginary part of an object
// 4- reciprocal() which return the reciprocal of a complex number object
// 5- toString() which is used to transform complex number object to string for presentation

//*******************************************************************************************



public class CMPLX {
	
	private double RealPart;
	private double ImgPart;
	
	public CMPLX(){
		this.RealPart = 0.0;
		this.ImgPart = 0.0;
	}
	
	public CMPLX(double re,double im){
		this.RealPart = re;
		this.ImgPart = im;
	}
	
	public CMPLX Addition(CMPLX Object){
		double RealPart = this.RealPart + Object.RealPart;
		double ImgPart = this.ImgPart + Object.ImgPart;
		return new CMPLX(RealPart,ImgPart);
	}
	
	public CMPLX Substraction(CMPLX Object){
		double RealPart = this.RealPart - Object.RealPart;
		double ImgPart = this.ImgPart - Object.ImgPart;
		return new CMPLX(RealPart,ImgPart);
	}
	
	public double getRealPart(){
		return RealPart;
	}
	
	public double getImgPart(){
		return ImgPart;
	}
	
	public CMPLX reciprocal(){
		double abs = RealPart*RealPart + ImgPart*ImgPart;
		return new CMPLX(RealPart/abs, -ImgPart/abs);
	}
	

    public String toString() {
        if (ImgPart == 0) return RealPart + "";
        if (RealPart == 0) return ImgPart + "i";
        if (ImgPart <  0) return RealPart + " - " + (-ImgPart) + "i";
        return RealPart + " + " + ImgPart + "i";
    }
    
}
    
  
