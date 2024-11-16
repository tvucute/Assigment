package fpoly.vuntph53431.assignmentgd1.LoginScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

import fpoly.vuntph53431.assignmentgd1.DAO.ManagerDAO;
import fpoly.vuntph53431.assignmentgd1.Fragment.ProductFragment;
import fpoly.vuntph53431.assignmentgd1.Fragment.UserFragment;
import fpoly.vuntph53431.assignmentgd1.Manager.AppManager;
import fpoly.vuntph53431.assignmentgd1.R;

public class Main extends AppCompatActivity {
    private DrawerLayout drawerMenu;
    private Toolbar myToolbar;
    private FrameLayout myFramelayout;
    private NavigationView Nav;
    private Fragment fragment = null;

    private ManagerDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        drawerMenu = findViewById(R.id.drawermenu);
        myToolbar = findViewById(R.id.mytoolbar);
        myFramelayout = findViewById(R.id.myframelayout);
        Nav = findViewById(R.id.nav);

        dao = new ManagerDAO(Main.this);

        fragment = ProductFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.myframelayout,fragment).commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Main.this,drawerMenu,myToolbar,0 ,0);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        drawerMenu.addDrawerListener(toggle);

        Nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.qlnv){
                    fragment = UserFragment.newInstance();
                    myToolbar.setTitle("Quản lý nhân viên");
                } else if (item.getItemId()==R.id.qlsp) {
                    fragment = ProductFragment.newInstance();
                    myToolbar.setTitle("Quản lý sản phẩm");
                } else if (item.getItemId()==R.id.signout) {
                    fragment = ProductFragment.newInstance();
                    Intent intent = new Intent(Main.this, LoginMain.class);
                    startActivity(intent);
                    finish();
                } else if (item.getItemId() == R.id.exit) {
                    finish();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.myframelayout,fragment).commit();
                drawerMenu.close();
                return true;
            }
        });

        Menu navMenu = Nav.getMenu();

        if (AppManager.shared().isAdmin) {
            navMenu.findItem(R.id.qlnv).setVisible(true);
        } else {
            navMenu.findItem(R.id.qlnv).setVisible(false);
        }
    }
}