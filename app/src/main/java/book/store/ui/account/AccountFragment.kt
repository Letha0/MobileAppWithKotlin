package book.store.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import book.store.R
import book.store.SessionManager
import book.store.activities.LoginActivity
import kotlinx.android.synthetic.main.fragment_account.*
import androidx.navigation.fragment.findNavController
import book.store.activities.AdminActivity


class AccountFragment : Fragment() {

    companion object {
        fun newInstance() = AccountFragment()
    }

    private val user = "admin@bookstore.io"
    private lateinit var viewModel: AccountViewModel

    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())
        button.setOnClickListener {
            if(session.isLoggedIn())
            {
                if(session.EMAIL==user)
                startActivity(Intent(requireContext(), AdminActivity::class.java))
                else
                findNavController().navigate(R.id.navigate_to_profile_fragment)
            }
            else{
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)}
        }

        btn_logout.setOnClickListener{
            session.Logout()
        }

    }


}
