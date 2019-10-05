package korotchenko.financemanager.presentation.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.view.menu.MenuBuilder
import androidx.wear.widget.drawer.WearableNavigationDrawerView
import korotchenko.financemanager.R

class MainNavigationAdapter(
    context: Context
) : WearableNavigationDrawerView.WearableNavigationDrawerAdapter() {

    private val menuList: Menu

    init {
        menuList = MenuBuilder(context)
        MenuInflater(context).inflate(R.menu.main_menu, menuList)
    }

    override fun getItemText(pos: Int): CharSequence = menuList.getItem(pos).title

    override fun getItemDrawable(pos: Int): Drawable = menuList.getItem(pos).icon

    override fun getCount(): Int = menuList.size()
}