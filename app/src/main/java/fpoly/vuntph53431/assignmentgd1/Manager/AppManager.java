package fpoly.vuntph53431.assignmentgd1.Manager;

public class AppManager {
    private static AppManager instance;
    public boolean isAdmin = false;

    private AppManager() {
        // Không cho phép khởi tạo từ bên ngoài.
    }

    public static synchronized AppManager shared() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }
}
