package theme.support.demo;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import skin.support.SkinCompatManager;
import skin.support.utils.SkinPreference;

public class BaseActivity extends AppCompatActivity {
    protected void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Title");
        toolbar.setSubtitle("Subtitle");
        toolbar.setNavigationIcon(R.drawable.ic_settings_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(BaseActivity.this, SettingsActivity.class));
                if (SkinPreference.getInstance().getSkinName().equals("")) {
                    SkinCompatManager.getInstance().loadSkin("night.theme", null, SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS);
                } else {
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                }
            }
        });
//        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_camera_24dp));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }
}
