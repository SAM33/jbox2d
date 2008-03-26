/*
 * JBox2D - A Java Port of Erin Catto's Box2D
 * 
 * JBox2D homepage: http://jbox2d.sourceforge.net/ 
 * Box2D homepage: http://www.box2d.org
 * 
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will the authors be held liable for any damages
 * arising from the use of this software.
 * 
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 * 
 * 1. The origin of this software must not be misrepresented; you must not
 * claim that you wrote the original software. If you use this software
 * in a product, an acknowledgment in the product documentation would be
 * appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 * misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 */

package org.jbox2d.common;

public class Mat22 {
    public Vec2 col1, col2;
    
    public String toString() {
    	String s = "";
    	s += "["+col1.x+","+col2.x+"]\n";
    	s += "["+col1.y+","+col2.y+"]";
    	return s;
    }

    public Mat22() {
        this(new Vec2(), new Vec2());
    }

    public Mat22(float angle) {
        this();
        setAngle(angle);
    }

    public Mat22(Vec2 c1, Vec2 c2) {
        col1 = c1.clone();
        col2 = c2.clone();
    }

    public Mat22(float col1x, float col2x, float col1y, float col2y) {
        col1 = new Vec2(col1x, col1y);
        col2 = new Vec2(col2x, col2y);
    }

    public void set(Mat22 m) {
        col1.x = m.col1.x;
        col1.y = m.col1.y;
        col2.x = m.col2.x;
        col2.y = m.col2.y;
    }

    public Mat22 clone() {
        return new Mat22(this.col1.clone(), this.col2.clone());
    }
    
    public void set(float angle) {
		float c = (float)Math.cos(angle), s = (float)Math.sin(angle);
		col1.x = c; col2.x = -s;
		col1.y = s; col2.y = c;
	}

    public void setIdentity() {
        col1.x = 1.0f;
        col2.x = 0.0f;
        col1.y = 0.0f;
        col2.y = 1.0f;
    }

    public void setZero() {
        col1.x = 0.0f;
        col2.x = 0.0f;
        col1.y = 0.0f;
        col2.y = 0.0f;
    }

    public void setAngle(float angle) {
        float c = (float) Math.cos(angle);
        float s = (float) Math.sin(angle);
        col1.x = c;
        col2.x = -s;
        col1.y = s;
        col2.y = c;
    }

    public void set(Vec2 c1, Vec2 c2) {
        col1.x = c1.x;
        col2.x = c2.x;
        col1.y = c1.y;
        col2.y = c2.y;
    }

    /** Returns the inverted Mat22 - does NOT invert the matrix locally! */
    public Mat22 invert() {
        float a = col1.x, b = col2.x, c = col1.y, d = col2.y;
        Mat22 B = new Mat22();
        float det = a * d - b * c;
        // b2Assert(det != 0.0f);
        det = 1.0f / det;
        B.col1.x = det * d;
        B.col2.x = -det * b;
        B.col1.y = -det * c;
        B.col2.y = det * a;
        return B;
    }

    public Mat22 abs() {
        return new Mat22(col1.abs(), col2.abs());
    }
    
    public static Mat22 abs(Mat22 R) {
    	return R.abs();
    }

    public Vec2 mul(Vec2 v) {
        return new Vec2(col1.x * v.x + col2.x * v.y, col1.y * v.x + col2.y
                * v.y);
    }

    public Mat22 mul(Mat22 R) {
        Mat22 C = new Mat22();
        C.set(this.mul(R.col1), this.mul(R.col2));
        return C;
    }

    public Mat22 mulT(Mat22 B) {
        Vec2 c1 = new Vec2(Vec2.dot(this.col1, B.col1), Vec2.dot(this.col2,
                B.col1));
        Vec2 c2 = new Vec2(Vec2.dot(this.col1, B.col2), Vec2.dot(this.col2,
                B.col2));
        Mat22 C = new Mat22();
        C.set(c1, c2);
        return C;
    }

    public Vec2 mulT(Vec2 v) {
        return new Vec2(Vec2.dot(v, col1), Vec2.dot(v, col2));
    }

    public Mat22 add(Mat22 B) {
        return new Mat22(col1.add(B.col1), col2.add(B.col2));
    }
    
    public Mat22 addLocal(Mat22 B) {
    	col1.addLocal(B.col1);
    	col2.addLocal(B.col2);
    	return this;
    }
    
    // Solve A * x = b
    public Vec2 solve(Vec2 b) {
        float a11 = col1.x, a12 = col2.x, a21 = col1.y, a22 = col2.y;
        float det = a11 * a22 - a12 * a21;
        assert(det != 0.0f);
        det = 1.0f / det;
        Vec2 x = new Vec2( det * (a22 * b.x - a12 * b.y),
                           det * (a11 * b.y - a21 * b.x) );
        return x;
    }
    
    public static Vec2 mul(Mat22 R, Vec2 v) {
    	return R.mul(v);
    }
    
    public static Mat22 mul(Mat22 A, Mat22 B){
    	return A.mul(B);
    }
   
    public static Vec2 mulT(Mat22 R, Vec2 v) {
    	return R.mulT(v);
    }
    
    public static Mat22 mulT(Mat22 A, Mat22 B){
    	return A.mulT(B);
    }
}
