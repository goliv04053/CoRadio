package com.xenione.libs.swipemaker.orientation

import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.OverScroller
import androidx.core.view.ViewCompat
import com.xenione.libs.swipemaker.Anchors
import com.xenione.libs.swipemaker.Position
import com.xenione.libs.swipemaker.ScrollerHelper
import com.xenione.libs.swipemaker.SwipeLayout

/**
 * Created by Eugeni on 28/09/2016.
 */
abstract class OrientationStrategy @JvmOverloads constructor(
    private val mView: View,
    internal val mTouchSlop: Int = ViewConfiguration.get(mView.context).scaledTouchSlop
) : Runnable {

    private val mPositionInfo = Position()
    private var mOnTranslateChangeListener: SwipeLayout.OnTranslateChangeListener? = null
    internal val mHelperScroller = ScrollerHelper(OverScroller(mView.context))
    var isDragDisabled = false

    fun setAnchor(vararg points: Int?) {
        mPositionInfo.setAnchors(Anchors.make(points.filterNotNull().toTypedArray()))
    }

    fun setOnTranslateChangeListener(listener: SwipeLayout.OnTranslateChangeListener?) {
        mOnTranslateChangeListener = listener
    }

    abstract fun onTouchEvent(event: MotionEvent): Boolean

    abstract fun onInterceptTouchEvent(event: MotionEvent): Boolean

    internal abstract fun getDelta(): Int

    internal abstract fun setDelta(delta: Int)

    fun translateBy(delta: Int) {
        translateTo(getDelta() + delta)
    }

    fun translateTo(distance: Int) {
        val cropped = ensureInsideBounds(distance)
        if (getDelta() == cropped) {
            return
        }
        setDelta(cropped)
        updatePosition(cropped)
    }

    private fun updatePosition(newPosition: Int) {
        mPositionInfo.updatePosition(newPosition)
        notifyListener()
    }

    private fun notifyListener() {
        mOnTranslateChangeListener?.onTranslateChange(
            mPositionInfo.mGlobal, mPositionInfo.mSection, mPositionInfo.mRelative
        )
    }

    private fun ensureInsideBounds(x: Int): Int {
        return mPositionInfo.cropInLimits(x)
    }

    internal fun fling(): Boolean {
        val start = getDelta()
        val end = endPositionFrom(start)
        val started = mHelperScroller.startScroll(start, end)
        ViewCompat.postOnAnimation(mView, this)
        return started
    }

    override fun run() {
        if (mHelperScroller.computeScrollOffset()) {
            translateTo(mHelperScroller.currX)
            ViewCompat.postOnAnimation(mView, this)
        }
    }

    private fun endPositionFrom(currPosition: Int): Int {
        return mPositionInfo.closeTo(currPosition)
    }

    internal fun disallowParentInterceptTouchEvent(disallow: Boolean) {
        mView.parent?.requestDisallowInterceptTouchEvent(disallow)
    }
}
