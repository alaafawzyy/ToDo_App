package fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.todo.com.example.todo_app.ui.other.modes
import com.example.todo.databinding.FragmentSettingsBinding
import android.widget.ArrayAdapter
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.ContextCompat


class SettingFragment:Fragment() {
    lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, modes)
        binding.autoCompleteModeTv.setAdapter(adapter)

        binding.autoCompleteModeTv.setOnItemClickListener { parent, view, position, id ->
            when (parent.getItemAtPosition(position).toString()) {
                "Dark" -> {AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                "Light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }
}