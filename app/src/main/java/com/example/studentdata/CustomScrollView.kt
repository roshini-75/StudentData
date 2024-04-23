package com.example.studentdata

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ScrollView

class CustomScrollView : ScrollView {

    private var keyboardVisible: Boolean = false
    private var lastScrollY: Int = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        initKeyboardListener()
    }

    private fun initKeyboardListener() {
        // Add a global layout listener to the root view to detect keyboard visibility changes
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = android.graphics.Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom
            // Determine keyboard visibility based on keypad height
            keyboardVisible = keypadHeight > screenHeight * 0.15
            // If keyboard is not visible, scroll to the last scroll position
            if (!keyboardVisible) {
                smoothScrollTo(0, lastScrollY)
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            // Save the last scroll position when touching the screen
            val focusedView = findFocus()
            if (focusedView is EditText) {
                lastScrollY = scrollY
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            // Hide the keyboard when touching the screen
            val focusedView = findFocus()
            if (focusedView is EditText) {
                hideKeyboard()
            }
        }
        return super.onTouchEvent(ev)
    }

    private fun hideKeyboard() {
        // Hide the soft keyboard using the InputMethodManager
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
