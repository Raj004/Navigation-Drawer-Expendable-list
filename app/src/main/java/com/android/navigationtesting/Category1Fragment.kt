package com.android.navigationtesting


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

/*Created by: Quintus Labs
  *www.quintuslabs.com
  *Created On: 27/04/2018
  * */

/**
 * A simple [Fragment] subclass.
 */
class Category1Fragment : Fragment() {

    internal var title = "Category1"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val textView = view.findViewById<TextView>(R.id.text_view)
        textView.text = "Category1 Fragment"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //you can set the title for your toolbar here for different fragments different titles
        activity!!.title = title
    }
}// Required empty public constructor
