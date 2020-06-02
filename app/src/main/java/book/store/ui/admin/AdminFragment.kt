package book.store.ui.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import book.store.R
import book.store.SessionManager
import book.store.activities.MainActivity

class AdminFragment: Fragment() {

    lateinit var session: SessionManager
    private var isFirstBackPressed = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }


    override fun onStart() {

        session = SessionManager(requireContext())

        if(System.currentTimeMillis()>=session.ExpiredDate){

            session.Logout()
        }
        super.onStart()
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if(isFirstBackPressed){

                        SessionManager.getInstance(requireContext()).Logout()
                        Toast.makeText(requireContext(), "You have been logout", Toast.LENGTH_SHORT).show()
                        activity?.finish()
                    } else {
                        isFirstBackPressed = true
                        Toast.makeText(requireContext(), "Press back again to logout", Toast.LENGTH_SHORT).show()
                        Handler().postDelayed({
                            isFirstBackPressed = false

                        }, 3000)
                    }
                }

            })
    }
}