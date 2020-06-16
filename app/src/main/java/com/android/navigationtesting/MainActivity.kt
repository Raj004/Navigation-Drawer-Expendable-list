package com.android.navigationtesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.android.navigationtesting.navigation.BaseItem
import com.android.navigationtesting.navigation.CustomDataProvider
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.toolbar.*
import pl.openrnd.multilevellistview.ItemInfo
import pl.openrnd.multilevellistview.MultiLevelListAdapter
import pl.openrnd.multilevellistview.MultiLevelListView
import pl.openrnd.multilevellistview.OnItemClickListener

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private var multiLevelListView: MultiLevelListView? = null
    private val mOnItemClickListener = object : OnItemClickListener {

        private fun showItemDescription(`object`: Any, itemInfo: ItemInfo) {

            if ((`object` as BaseItem).getName().contains("Home")) {
                displaySelectedScreen("HOME")
            }
            if ((`object` as BaseItem).getName().contains("Category1")) {
                displaySelectedScreen("CATEGORY1")
            }
            if ((`object` as BaseItem).getName().contains("Category2")) {
                displaySelectedScreen("CATEGORY2")
            }
            if ((`object` as BaseItem).getName().contains("Category3")) {
                displaySelectedScreen("CATEGORY3")
            }
            if ((`object` as BaseItem).getName().contains("Assignment1")) {
                displaySelectedScreen("ASSIGNMENT1")
            }
            if ((`object` as BaseItem).getName().contains("Assignment2")) {
                displaySelectedScreen("ASSIGNMENT2")
            }
            if ((`object` as BaseItem).getName().contains("Assignment3")) {
                displaySelectedScreen("ASSIGNMENT3")
            }
            if ((`object` as BaseItem).getName().contains("Assignment4")) {
                displaySelectedScreen("ASSIGNMENT4")
            }
            if ((`object` as BaseItem).getName().contains("Help")) {
                displaySelectedScreen("HELP")
            }
            if ((`object` as BaseItem).getName().contains("AboutUs")) {
                displaySelectedScreen("ABOUTUS")
            }


        }

        override fun onItemClicked(
            parent: MultiLevelListView,
            view: View,
            item: Any,
            itemInfo: ItemInfo
        ) {
            showItemDescription(item, itemInfo)
        }

        override fun onGroupItemClicked(
            parent: MultiLevelListView,
            view: View,
            item: Any,
            itemInfo: ItemInfo
        ) {
            showItemDescription(item, itemInfo)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWindow()



        confMenu()
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        //drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        displaySelectedScreen("HOME")
    }

    private fun confMenu() {
        multiLevelListView = findViewById(R.id.multi_nav) as MultiLevelListView
        // custom ListAdapter
        val listAdapter = ListAdapter()
        multiLevelListView!!.setAdapter(listAdapter)
        multiLevelListView!!.setOnItemClickListener(mOnItemClickListener)

        listAdapter.setDataItems(CustomDataProvider.getInitialItems())
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


  /*  override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }*/

    private fun displaySelectedScreen(itemName: String) {

        //creating fragment object
        var fragment: Fragment? = null

        //initializing the fragment object which is selected
        when (itemName) {
//            "HOME" -> fragment = HomeFragment()
            "CATEGORY1" -> fragment = Category1Fragment()
            "CATEGORY2" -> fragment = Category2Fragment()
            "CATEGORY3" -> Toast.makeText(
                applicationContext,
                "Category3 Clicked",
                Toast.LENGTH_LONG
            ).show()
            "ASSIGNMENT1" -> fragment = Assignment1Fragment()
            "ASSIGNMENT2" -> fragment = Assignment2Fragment()
            "ASSIGNMENT3" -> fragment = Assignment1Fragment()
            "ASSIGNMENT4" -> fragment = Assignment2Fragment()
            "HELP" -> startActivity(Intent(applicationContext, HelpActivity::class.java))
            "ABOUTUS" -> startActivity(Intent(applicationContext, AboutUsActivity::class.java))
        }

        //replacing the fragment
        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.content_frame, fragment)
            ft.commit()
            val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
            drawer.closeDrawer(GravityCompat.START)
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.itemId.toString())
        //make this method blank
        return true
    }


    private inner class ListAdapter : MultiLevelListAdapter() {

        public override fun getSubObjects(`object`: Any): List<*> {
            // DIEKSEKUSI SAAT KLIK PADA GROUP-ITEM
            return CustomDataProvider.getSubItems(`object` as BaseItem)
        }

        public override fun isExpandable(`object`: Any): Boolean {
            return CustomDataProvider.isExpandable(`object` as BaseItem)
        }

        public override fun getViewForObject(
            `object`: Any,
            convertView: View?,
            itemInfo: ItemInfo
        ): View {
            var convertView = convertView
            val viewHolder: ViewHolder
            if (convertView == null) {
                viewHolder = ViewHolder()
                convertView =
                    LayoutInflater.from(this@MainActivity).inflate(R.layout.data_item, null)
                //viewHolder.infoView = (TextView) convertView.findViewById(R.id.dataItemInfo);
                viewHolder.nameView = convertView!!.findViewById(R.id.dataItemName)
                viewHolder.arrowView = convertView.findViewById(R.id.dataItemArrow)
                viewHolder.icon = convertView.findViewById(R.id.di_image)

                convertView.tag = viewHolder
            } else {
                viewHolder = convertView.tag as ViewHolder
            }

            viewHolder.nameView!!.setText((`object` as BaseItem).getName())
            //viewHolder.infoView.setText(getItemInfoDsc(itemInfo));

            if (itemInfo.isExpandable) {
                viewHolder.arrowView!!.visibility = View.VISIBLE
                viewHolder.arrowView!!.setImageResource(
                    if (itemInfo.isExpanded)
                        R.drawable.bottomarrow
                    else
                        R.drawable.rightarrow
                )
            } else {
                viewHolder.arrowView!!.visibility = View.GONE
            }
            viewHolder.icon!!.setImageResource((`object` as BaseItem).getIcon())
            return convertView
        }

        private inner class ViewHolder {
            internal var nameView: TextView? = null
            internal var arrowView: ImageView? = null
            internal var icon: ImageView? = null

        }
    }


    fun initWindow() {
        this.window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
      //  supportActionBar!!.hide()
    }
}
