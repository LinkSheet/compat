package fe.linksheet.compat

import android.content.ComponentName
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    private val versions = listOf("", ".pro")
    private val suffixes = listOf(".debug", ".nightly")

    private val pkg = "fe.linksheet"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            v.setPadding(0, 0, 0, 0)
            insets
        }

        window.setBackgroundDrawable(ColorDrawable(0))
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else WindowManager.LayoutParams.TYPE_SYSTEM_ALERT

        window.setType(type)

        if (intent != null) {
            for (version in versions) {
                for (suffix in suffixes) {
                    runCatching {
                        startActivity(
                            Intent()
                                .setAction(Intent.ACTION_VIEW)
                                .addCategory(Intent.CATEGORY_BROWSABLE)
                                .setData(intent.data)
                                .setComponent(ComponentName("$pkg$version$suffix", "$pkg.activity.BottomSheetActivity"))
                        )
                        finish()
                    }
                }
            }
        }
    }
}
