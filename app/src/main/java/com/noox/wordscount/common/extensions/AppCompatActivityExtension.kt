import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding> AppCompatActivity.initBinding(@LayoutRes layout: Int): T {
    val binding = DataBindingUtil.setContentView<T>(this, layout)
    binding.lifecycleOwner = this
    return binding
}
