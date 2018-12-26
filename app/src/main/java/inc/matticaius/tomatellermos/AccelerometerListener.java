package inc.matticaius.tomatellermos;

public interface AccelerometerListener {
    void onAccelerationChanged(float x, float y, float z);

    void onShake(float force);
}
