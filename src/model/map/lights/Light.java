package model.map.lights;

public class Light {

    private int dir, intensity;

    public Light(int dir, int intensity) {
        this.dir = dir;
        this.intensity = intensity;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }
}
