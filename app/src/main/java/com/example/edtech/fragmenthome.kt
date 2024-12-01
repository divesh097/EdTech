package com.example.edtech

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.razorpay.Checkout


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [fragmenthome.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragmenthome : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var userid:String?=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            userid=it.getString("UID")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_fragmenthome, container, false)
        if(userid==null){
            requireActivity().finish()
        }

//        Log.d("hello", "${userid}")
        val ll:LottieAnimationView=view.findViewById(R.id.loading)
        ll.visibility=View.VISIBLE
        ll.playAnimation()
        ll.loop(true)
        var recyclercourse:RecyclerView
        var allcourse=ArrayList<datacour>()
        allcourse=ArrayList()
        firestore("aa").gethome{courselist->
            allcourse.addAll(courselist)
//            Log.d("data","${allcourse}")
            if(allcourse.size!=0) {
                ll.pauseAnimation()
                ll.visibility = View.GONE
                recyclercourse = view.findViewById(R.id.homecourse)
                recyclercourse.layoutManager = LinearLayoutManager(requireContext())
                recyclercourse.adapter = adaptercourse(requireContext(), allcourse,userid.toString())
            }

        }


        return  view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragmenthome.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragmenthome().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString("UID",userid)
                }
            }
    }
}