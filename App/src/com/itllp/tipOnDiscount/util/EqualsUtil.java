// Copyright 2011-2012 David A. Greenbaum
/*
This file is part of Tip On Discount.

Tip On Discount is free software: you can redistribute it and/or
modify it under the terms of the GNU General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Tip On Discount is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with Tip On Discount.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.itllp.tipOnDiscount.util;

/**
* Collected methods that allow easy implementation of <code>equals</code>.
*
* Example use case in a class called Car:
* <pre>
public boolean equals(Object aThat){
  if ( this == aThat ) return true;
  if ( !(aThat instanceof Car) ) return false;
  Car that = (Car)aThat;
  return
    EqualsUtil.areEqual(this.fName, that.fName) &&
    EqualsUtil.areEqual(this.fNumDoors, that.fNumDoors) &&
    EqualsUtil.areEqual(this.fGasMileage, that.fGasMileage) &&
    EqualsUtil.areEqual(this.fColor, that.fColor) &&
    Arrays.equals(this.fMaintenanceChecks, that.fMaintenanceChecks); //array!
}
* </pre>
*
* <em>Arrays are not handled by this class</em>.
* This is because the <code>Arrays.equals</code> methods should be used for
* array fields.
*/
public final class EqualsUtil {

  static public boolean areEqual(boolean aThis, boolean aThat){
    return aThis == aThat;
  }

  static public boolean areEqual(char aThis, char aThat){
    return aThis == aThat;
  }

  static public boolean areEqual(long aThis, long aThat){
    /*
    * Implementation Note
    * Note that byte, short, and int are handled by this method, through
    * implicit conversion.
    */
    return aThis == aThat;
  }

  static public boolean areEqual(float aThis, float aThat){
	  return Float.floatToIntBits(aThis) == Float.floatToIntBits(aThat);
  }

  
  static public boolean areEqual(double aThis, double aThat){
    return Double.doubleToLongBits(aThis) == Double.doubleToLongBits(aThat);
  }

  /**
  * Possibly-null object field.
  *
  * Includes type-safe enumerations and collections, but does not include
  * arrays. See class comment.
  */
  static public boolean areEqual(Object aThis, Object aThat){
    return aThis == null ? aThat == null : aThis.equals(aThat);
  }
}
