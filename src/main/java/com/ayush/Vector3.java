package com.ayush;

public class Vector3 {
    public double x;
    public double y;
    public double z;

    public Vector3(double x,double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public Vector3 rotateX(double angle){
        double sin=Math.sin(angle);
        double cos=Math.cos(angle);

        double newX=x;
        double newY=y*cos-z*sin;
        double newZ=y*sin+z*cos;

        return new Vector3(newX,newY,newZ);
    }

    public Vector3 rotateY(double angle){
        double sin=Math.sin(angle);
        double cos=Math.cos(angle);

        double newX=x*cos+z*sin;
        double newY=y;
        double newZ=-x*sin+z*cos;

        return new Vector3(newX,newY,newZ);
    }

    public Vector3 rotateZ(double angle){
        double sin=Math.sin(angle);
        double cos=Math.cos(angle);

        double newX=x*cos-y*sin;
        double newY=x*sin+y*cos;
        double newZ=z;

        return new Vector3(newX,newY,newZ);
    }

}
