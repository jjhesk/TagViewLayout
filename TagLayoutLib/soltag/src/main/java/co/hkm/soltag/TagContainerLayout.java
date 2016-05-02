package co.hkm.soltag;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.hkm.soltag.ext.LayouMode;

/**
 * Author: lujun(http://blog.lujun.co)
 * Date: 2015-12-30 17:14
 */
public class TagContainerLayout extends ViewGroup {

    /**
     * Vertical interval, default 5(dp)
     */
    private int mVerticalInterval;

    /**
     * Horizontal interval, default 5(dp)
     */
    private int mHorizontalInterval;

    /**
     * TagContainerLayout border width(default 0.5dp)
     */
    private float mBorderWidth = 0.5f;

    /**
     * TagContainerLayout border radius(default 10.0dp)
     */
    private float mBorderRadius = 10.0f;

    /**
     * The sensitive of the ViewDragHelper(default 1.0f, normal)
     */
    private float mSensitivity = 1.0f;

    /**
     * TagView average height
     */
    private int mChildHeight;

    /**
     * TagContainerLayout border color(default #22FF0000)
     */
    private int mBorderColor = Color.parseColor("#22FF0000");

    /**
     * TagContainerLayout background color(default #11FF0000)
     */
    private int mBackgroundColor = Color.parseColor("#11FF0000");

    /**
     * The container layout gravity(default left)
     */
    private int mGravity = Gravity.LEFT;

    /**
     * The max length for TagView(default max length 23)
     */
    private int mTagMaxLength = 23;

    /**
     * TagView Border width(default 0.5dp)
     */
    private float mTagBorderWidth = 0.5f;

    /**
     * TagView Border radius(default 15.0dp)
     */
    private float mTagBorderRadius = 15.0f;

    /**
     * TagView Text size(default 14sp)
     */
    private float mTagTextSize = 14;

    /**
     * Text direction(support:TEXT_DIRECTION_RTL & TEXT_DIRECTION_LTR, default TEXT_DIRECTION_LTR)
     */
    private int mTagTextDirection = View.TEXT_DIRECTION_LTR;

    /**
     * Horizontal padding for TagView, include left & right padding(left & right padding are equal, default 20px)
     */
    private int mTagHorizontalPadding = 20;

    /**
     * Vertical padding for TagView, include top & bottom padding(top & bottom padding are equal, default 17px)
     */
    private int mTagVerticalPadding = 17;

    /**
     * TagView border color(default #88F44336)
     */
    private int mTagBorderColor = Color.parseColor("#88F44336");

    /**
     * TagView background color(default #33F44336)
     */
    private int mTagBackgroundColor = Color.parseColor("#33F44336");

    /**
     * TagView text color(default #FF666666)
     */
    private int mTagTextColor = Color.parseColor("#FF666666");

    /**
     * TagView typeface
     */
    private Typeface mTagTypeface = Typeface.DEFAULT;

    /**
     * Whether TagView can clickable(default unclickable)
     */
    private boolean isTagViewClickable;
    /**
     * for alternative selecting drawable
     */
    private Drawable itemDrawable;
    /**
     * Preselected positions of the tag
     */
    private int[] mPreselectedTags;
    /**
     * Tags
     */
    private List<String> mTags;

    /**
     * Can drag TagView(default false)
     */
    private boolean mDragEnable;

    /**
     * TagView drag state(default STATE_IDLE)
     */
    private int mTagViewState = ViewDragHelper.STATE_IDLE;

    /**
     * The distance between baseline and descent(default 5.5px)
     */
    private float mTagBdDistance = 5.5f;

    /**
     * The interaction mode from the factory
     */
    private LayouMode mMode = LayouMode.DEFAULT;
    /**
     * OnTagClickListener for TagView
     */
    private TagView.OnTagClickListener mOnTagClickListener;

    private Paint mPaint;

    private RectF mRectF;

    private ViewDragHelper mViewDragHelper;

    private List<View> mChildViews;

    private int[] mViewPos;
    private int[] profile_normal, profile_active, mThemeOnPreselected;
    private Context mConx;
    /**
     * View theme default PURE_CYAN)
     */
    private int mTheme = ColorFactory.PURE_CYAN;
    /**
     * View theme default PURE_CYAN
     */
    private int mThemeActive = ColorFactory.RANDOM;
    /**
     * Default interval(dp)
     */
    private static final float DEFAULT_INTERVAL = 5;

    /**
     * Default tag min length
     */
    private static final int TAG_MIN_LENGTH = 3;

    public TagContainerLayout(Context context) {
        this(context, null);
    }

    public TagContainerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mConx = context;
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.AndroidTagView,
                defStyleAttr, 0);
        mVerticalInterval = (int) attributes.getDimension(R.styleable.AndroidTagView_vertical_interval,
                dp2px(context, DEFAULT_INTERVAL));
        mHorizontalInterval = (int) attributes.getDimension(R.styleable.AndroidTagView_horizontal_interval,
                dp2px(context, DEFAULT_INTERVAL));
        mBorderWidth = attributes.getDimension(R.styleable.AndroidTagView_container_border_width,
                dp2px(context, mBorderWidth));
        mBorderRadius = attributes.getDimension(R.styleable.AndroidTagView_container_border_radius,
                dp2px(context, mBorderRadius));
        mTagBdDistance = attributes.getDimension(R.styleable.AndroidTagView_tag_bd_distance,
                mTagBdDistance);
        mBorderColor = attributes.getColor(R.styleable.AndroidTagView_container_border_color,
                mBorderColor);
        mBackgroundColor = attributes.getColor(R.styleable.AndroidTagView_container_background_color,
                mBackgroundColor);
        mDragEnable = attributes.getBoolean(R.styleable.AndroidTagView_container_enable_drag, false);
        mSensitivity = attributes.getFloat(R.styleable.AndroidTagView_container_drag_sensitivity,
                mSensitivity);
        mGravity = attributes.getInt(R.styleable.AndroidTagView_container_gravity, mGravity);
        mTagMaxLength = attributes.getInt(R.styleable.AndroidTagView_tag_max_length, mTagMaxLength);
        mTheme = attributes.getInt(R.styleable.AndroidTagView_tag_theme, mTheme);
        mTagBorderWidth = attributes.getDimension(R.styleable.AndroidTagView_tag_border_width,
                dp2px(context, mTagBorderWidth));
        mTagBorderRadius = attributes.getDimension(
                R.styleable.AndroidTagView_tag_corner_radius, dp2px(context, mTagBorderRadius));
        mTagHorizontalPadding = (int) attributes.getDimension(
                R.styleable.AndroidTagView_tag_horizontal_padding, mTagHorizontalPadding);
        mTagVerticalPadding = (int) attributes.getDimension(
                R.styleable.AndroidTagView_tag_vertical_padding, mTagVerticalPadding);
        mTagTextSize = attributes.getDimension(R.styleable.AndroidTagView_tag_text_size,
                sp2px(context, mTagTextSize));
        mTagBorderColor = attributes.getColor(R.styleable.AndroidTagView_tag_border_color,
                mTagBorderColor);
        mTagBackgroundColor = attributes.getColor(R.styleable.AndroidTagView_tag_background_color,
                mTagBackgroundColor);
        mTagTextColor = attributes.getColor(R.styleable.AndroidTagView_tag_text_color, mTagTextColor);
        mTagTextDirection = attributes.getInt(R.styleable.AndroidTagView_tag_text_direction, mTagTextDirection);
        isTagViewClickable = attributes.getBoolean(R.styleable.AndroidTagView_tag_clickable, false);
        Drawable dr = attributes.getDrawable(R.styleable.AndroidTagView_tag_drawable);
        if (dr != null) {
            //Drawable l = ContextCompat.getDrawable(mConx, dr);
            itemDrawable = dr;
        }
        String font_name = attributes.getString(R.styleable.AndroidTagView_tag_fontface);
        mTagTypeface = helper.getTypface(font_name, context);
        attributes.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectF = new RectF();
        mChildViews = new ArrayList<View>();
        mViewDragHelper = ViewDragHelper.create(this, mSensitivity, new DragHelperCallBack());
        setWillNotDraw(false);
        setTagMaxLength(mTagMaxLength);
        setTagHorizontalPadding(mTagHorizontalPadding);
        setTagVerticalPadding(mTagVerticalPadding);
        profile_active = onUpdateColorFactory(mThemeActive);
        profile_normal = onUpdateColorFactory(mTheme);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        final int childCount = getChildCount();
        int lines = childCount == 0 ? 0 : getChildLines(childCount);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (heightSpecMode == MeasureSpec.AT_MOST
                || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            setMeasuredDimension(widthSpecSize, (mVerticalInterval + mChildHeight) * lines
                    - mVerticalInterval + getPaddingTop() + getPaddingBottom());
        } else {
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.set(0, 0, w, h);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount;
        if ((childCount = getChildCount()) <= 0) {
            return;
        }
        int availableW = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int curRight = getMeasuredWidth() - getPaddingRight();
        int curTop = getPaddingTop();
        int curLeft = getPaddingLeft();
        int sPos = 0;
        mViewPos = new int[childCount * 2];

        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != GONE) {
                int width = childView.getMeasuredWidth();
                if (mGravity == Gravity.RIGHT) {
                    if (curRight - width < getPaddingLeft()) {
                        curRight = getMeasuredWidth() - getPaddingRight();
                        curTop += mChildHeight + mVerticalInterval;
                    }
                    mViewPos[i * 2] = curRight - width;
                    mViewPos[i * 2 + 1] = curTop;
                    curRight -= width + mHorizontalInterval;
                } else if (mGravity == Gravity.CENTER) {
                    if (curLeft + width - getPaddingLeft() > availableW) {
                        int leftW = getMeasuredWidth() - mViewPos[(i - 1) * 2]
                                - getChildAt(i - 1).getMeasuredWidth() - getPaddingRight();
                        for (int j = sPos; j < i; j++) {
                            mViewPos[j * 2] = mViewPos[j * 2] + leftW / 2;
                        }
                        sPos = i;
                        curLeft = getPaddingLeft();
                        curTop += mChildHeight + mVerticalInterval;
                    }
                    mViewPos[i * 2] = curLeft;
                    mViewPos[i * 2 + 1] = curTop;
                    curLeft += width + mHorizontalInterval;

                    if (i == childCount - 1) {
                        int leftW = getMeasuredWidth() - mViewPos[i * 2]
                                - childView.getMeasuredWidth() - getPaddingRight();
                        for (int j = sPos; j < childCount; j++) {
                            mViewPos[j * 2] = mViewPos[j * 2] + leftW / 2;
                        }
                    }
                } else {
                    if (curLeft + width - getPaddingLeft() > availableW) {
                        curLeft = getPaddingLeft();
                        curTop += mChildHeight + mVerticalInterval;
                    }
                    mViewPos[i * 2] = curLeft;
                    mViewPos[i * 2 + 1] = curTop;
                    curLeft += width + mHorizontalInterval;
                }
            }
        }

        // layout all child views
        for (int i = 0; i < mViewPos.length / 2; i++) {
            View childView = getChildAt(i);
            childView.layout(mViewPos[i * 2], mViewPos[i * 2 + 1],
                    mViewPos[i * 2] + childView.getMeasuredWidth(),
                    mViewPos[i * 2 + 1] + mChildHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBackgroundColor);
        canvas.drawRoundRect(mRectF, mBorderRadius, mBorderRadius, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(mBorderColor);
        canvas.drawRoundRect(mRectF, mBorderRadius, mBorderRadius, mPaint);
    }

    public static class Builder {
        private int
                ic_company, ic_search, ic_back, ic_background,
                tb_textsize = 0, tb_title_color = 0, title_line_config = 1,
                animation_duration_logo = -1,
                animation_duration = -1;
        private Typeface typeface;
        private String title_default;
        private boolean enable_logo_anim = true;
        private boolean save_title_navigation = false;

        public Builder() {

        }

        public Builder setFontFace(@NonNull Context mContext, @NonNull final String fontNameInFontFolder) {
            typeface = helper.getTypface(fontNameInFontFolder, mContext);
            return this;
        }

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            requestLayout();
        }
    }

    private int getChildLines(int childCount) {
        int availableW = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int lines = 1;
        for (int i = 0, curLineW = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int dis = childView.getMeasuredWidth() + mHorizontalInterval;
            int height = childView.getMeasuredHeight();
            mChildHeight = i == 0 ? height : Math.min(mChildHeight, height);
            curLineW += dis;
            if (curLineW - mHorizontalInterval > availableW) {
                lines++;
                curLineW = dis;
            }
        }
        return lines;
    }

    private int[] onUpdateColorFactory(int import_theme) {
        int[] colors;
        if (import_theme == ColorFactory.RANDOM) {
            colors = ColorFactory.onRandomBuild();
        } else if (import_theme == ColorFactory.PURE_TEAL) {
            colors = ColorFactory.onPureBuild(ColorFactory.PURE_COLOR.TEAL);
        } else if (import_theme == ColorFactory.PURE_CYAN) {
            colors = ColorFactory.onPureBuild(ColorFactory.PURE_COLOR.CYAN);
        } else {
            ColorFactory cf = new ColorFactory(import_theme, mConx);
            if (cf.isUsingStylable()) {
                colors = cf.getConfig();
            } else {
                colors = new int[]{mTagBackgroundColor, mTagBorderColor, mTagTextColor};
            }
        }
        return colors;
    }

    private void onSetTag() {
        if (mTags == null || mTags.size() == 0) {
            return;
        }
        for (int i = 0; i < mTags.size(); i++) {
            onAddTag(mTags.get(i), mChildViews.size());
        }
        postInvalidate();
    }

    private void processPreselectedOptions(int position, TagView tag, int[] target_theme, boolean flag) {
        if (mMode == LayouMode.MULTIPLE_CHOICE || mMode == LayouMode.SINGLE_CHOICE) {
            boolean apply_original = true;
            if (mPreselectedTags != null && mThemeOnPreselected != null) {
                for (int i = 0; i < mPreselectedTags.length; i++) {
                    if (mPreselectedTags[i] == position) {
                        if (flag) {
                            tag.applyProfile(mThemeOnPreselected);
                        } else {
                            tag.applyProfile(target_theme);
                        }
                        tag.setFlag_on(flag);
                        apply_original = false;
                    }
                }
            }
            if (apply_original) {
                tag.applyProfile(target_theme);
                tag.setFlag_on(flag);
            }
        }
    }

    private void processPreselectedRender(int position, TagView tag) {
        if (mMode == LayouMode.MULTIPLE_CHOICE || mMode == LayouMode.SINGLE_CHOICE) {
            if (mPreselectedTags == null) return;
            for (int i = 0; i < mPreselectedTags.length; i++) {
                if (mPreselectedTags[i] == position) {
                    tag.setFlag_on(true);
                    tag.applyProfile(mThemeOnPreselected == null ? profile_active : mThemeOnPreselected);
                }
            }
        }
    }

    private void onAddTag(String text, int position) {
        if (position < 0 || position > mChildViews.size()) {
            throw new RuntimeException("Illegal position!");
        }
        TagView tagView = new TagView(getContext(), text);
        initTagView(tagView);

        mChildViews.add(position, tagView);
        if (position < mChildViews.size()) {
            for (int i = position; i < mChildViews.size(); i++) {
                mChildViews.get(i).setTag(i);
            }
        } else {
            tagView.setTag(position);
        }

        processPreselectedRender(position, tagView);
        addView(tagView, position);
    }

    private void initTagView(TagView tagView) {
        tagView.applyProfile(onUpdateColorFactory(mTheme));
        tagView.setTagMaxLength(mTagMaxLength);
        tagView.setTextDirection(mTagTextDirection);
        tagView.setTypeface(mTagTypeface);
        tagView.setBorderWidth(mTagBorderWidth);
        tagView.setBorderRadius(mTagBorderRadius);
        tagView.setTextSize(mTagTextSize);
        tagView.setHorizontalPadding(mTagHorizontalPadding);
        tagView.setVerticalPadding(mTagVerticalPadding);
        tagView.setIsViewClickable(isTagViewClickable);
        tagView.setBdDistance(mTagBdDistance);
        tagView.setOnTagClickListener(mOnTagClickListener);
        tagView.setMode(mMode);
        tagView.setNotification(this);
        if (itemDrawable != null) {
            tagView.setItemDrawable(itemDrawable);
        }
    }

    private void processPreselectedOptionsOff(final int position, TagView tag) {
        if (mPreselectedTags == null) return;
        for (int i = 0; i < mPreselectedTags.length; i++) {
            if (mPreselectedTags[i] == position) {
                tag.applyProfile(mThemeOnPreselected == null ? profile_normal : mThemeOnPreselected);
            } else {
                tag.applyProfile(profile_normal);
            }
        }
        tag.setFlag_on(false);
    }

    /**
     * only communicate from the TagView
     *
     * @param notepos position of the tag
     */
    public void notifyInternal(int notepos) {
        int onNotePosition = scopePosition(notepos);
        if (mMode == LayouMode.DEFAULT) {

        } else if (mMode == LayouMode.SINGLE_CHOICE) {
            for (int i = 0; i < mChildViews.size(); i++) {
                if (mChildViews.get(i) instanceof TagView) {
                    TagView tag = (TagView) mChildViews.get(i);
                    if (onNotePosition == i) {
                        tag.applyProfile(profile_active);
                        tag.setFlag_on(true);
                    } else {
                        tag.setFlag_on(false);
                        tag.applyProfile(profile_normal);
                        // processPreselectedOptionsOff(i, tag);
                    }
                    tag.postInvalidate();
                }
            }
        } else if (mMode == LayouMode.SINGLE_CHOICE_OVERLAY_PRESET) {
            for (int i = 0; i < mChildViews.size(); i++) {
                if (mChildViews.get(i) instanceof TagView) {
                    TagView tag = (TagView) mChildViews.get(i);
                    if (onNotePosition == i) {
                        tag.applyProfile(profile_active);
                        tag.setFlag_on(true);
                    } else {
                        processPreselectedOptionsOff(i, tag);
                    }
                    tag.postInvalidate();
                }
            }
        } else if (mMode == LayouMode.MULTIPLE_CHOICE) {
            if (mChildViews.get(onNotePosition) instanceof TagView) {
                TagView tag = (TagView) mChildViews.get(onNotePosition);
                if (tag.isFlag_on()) {
                    // setProfile(tag, profile_normal);
                    processPreselectedOptions(notepos, tag, profile_normal, false);
                } else {
                    // tag.setFlag_on(true);
                    //   ..   processPreselectedOptions(notepos, tag, profile_active);
                    //  setProfile(tag, profile_active);
                    processPreselectedOptions(notepos, tag, profile_active, true);
                }
                tag.postInvalidate();
            }
        }
    }

    private int scopePosition(int in) {
        int n1 = in >= mChildViews.size() ? mChildViews.size() - 1 : in;
        return n1 < 0 ? 0 : n1;
    }

    public final void performClick(int position_in_list, boolean isShortClick) {
        int pos = scopePosition(position_in_list);
        if (mChildViews.get(pos) instanceof TagView) {
            TagView cf = (TagView) mChildViews.get(pos);
            if (isShortClick) {
                mOnTagClickListener.onTagClick(pos, cf.getText());
            } else {
                mOnTagClickListener.onTagLongClick(pos, cf.getText());
            }
        }
        notifyInternal(pos);
    }

    public final void performFirstItemClick() {
        performClick(0, true);
    }

    public final void performLastItemClick() {
        performClick(mChildViews.size() - 1, true);
    }

    public final void performFirstItemLongClick() {
        performClick(0, false);
    }

    public final void performLastItemLongClick() {
        performClick(mChildViews.size() - 1, false);
    }


    private void onRemoveTag(int position) {
        if (position < 0 || position >= mChildViews.size()) {
            throw new RuntimeException("Illegal position!");
        }
        mChildViews.remove(position);
        removeViewAt(position);
        for (int i = position; i < mChildViews.size(); i++) {
            mChildViews.get(i).setTag(i);
        }
        // TODO, make removed view null?
    }

    private int[] onGetNewPosition(View view) {
        int left = view.getLeft();
        int top = view.getTop();
        int bestMatchLeft = mViewPos[(int) view.getTag() * 2];
        int bestMatchTop = mViewPos[(int) view.getTag() * 2 + 1];
        int tmpTopDis = Math.abs(top - bestMatchTop);
        for (int i = 0; i < mViewPos.length / 2; i++) {
            if (Math.abs(top - mViewPos[i * 2 + 1]) < tmpTopDis) {
                bestMatchTop = mViewPos[i * 2 + 1];
                tmpTopDis = Math.abs(top - mViewPos[i * 2 + 1]);
            }
        }
        int rowChildCount = 0;
        int tmpLeftDis = 0;
        for (int i = 0; i < mViewPos.length / 2; i++) {
            if (mViewPos[i * 2 + 1] == bestMatchTop) {
                if (rowChildCount == 0) {
                    bestMatchLeft = mViewPos[i * 2];
                    tmpLeftDis = Math.abs(left - bestMatchLeft);
                } else {
                    if (Math.abs(left - mViewPos[i * 2]) < tmpLeftDis) {
                        bestMatchLeft = mViewPos[i * 2];
                        tmpLeftDis = Math.abs(left - bestMatchLeft);
                    }
                }
                rowChildCount++;
            }
        }
        return new int[]{bestMatchLeft, bestMatchTop};
    }

    private int onGetCoordinateReferPos(int left, int top) {
        int pos = 0;
        for (int i = 0; i < mViewPos.length / 2; i++) {
            if (left == mViewPos[i * 2] && top == mViewPos[i * 2 + 1]) {
                pos = i;
            }
        }
        return pos;
    }

    private void onChangeView(View view, int newPos, int originPos) {
        mChildViews.remove(originPos);
        mChildViews.add(newPos, view);
        for (View child : mChildViews) {
            child.setTag(mChildViews.indexOf(child));
        }
        removeViewAt(originPos);
        addView(view, newPos);
    }

    private int ceilTagBorderWidth() {
        return (int) Math.ceil(mTagBorderWidth);
    }

    private class DragHelperCallBack extends ViewDragHelper.Callback {

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            mTagViewState = state;
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            requestDisallowInterceptTouchEvent(true);
            return mDragEnable;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            final int leftX = getPaddingLeft();
            final int rightX = getWidth() - child.getWidth() - getPaddingRight();
            return Math.min(Math.max(left, leftX), rightX);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topY = getPaddingTop();
            final int bottomY = getHeight() - child.getHeight() - getPaddingBottom();
            return Math.min(Math.max(top, topY), bottomY);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            requestDisallowInterceptTouchEvent(false);
            int[] pos = onGetNewPosition(releasedChild);
            int posRefer = onGetCoordinateReferPos(pos[0], pos[1]);
            onChangeView(releasedChild, posRefer, (int) releasedChild.getTag());
            mViewDragHelper.settleCapturedViewAt(pos[0], pos[1]);
            invalidate();
        }
    }

    /**
     * Get current drag view state.
     *
     * @return switchable state
     */
    public int getTagViewState() {
        return mTagViewState;
    }

    /**
     * Get TagView text baseline and descent distance.
     *
     * @return distance of the border
     */
    public float getTagBdDistance() {
        return mTagBdDistance;
    }

    /**
     * Set TagView text baseline and descent distance.
     *
     * @param tagBdDistance distance of the tag border
     */
    public void setTagBdDistance(float tagBdDistance) {
        this.mTagBdDistance = dp2px(getContext(), tagBdDistance);
    }


    /**
     * Set tags
     *
     * @param tags the array of tags
     */
    public void setTags(List<String> tags) {
        mTags = tags;
        onSetTag();
    }

    /**
     * Set tags
     *
     * @param tags tags in string array
     */
    public void setTags(String... tags) {
        mTags = Arrays.asList(tags);
        onSetTag();
    }

    /**
     * Inserts the specified TagView into this ContainerLayout at the end.
     *
     * @param text the string in tag
     */
    public void addTag(String text) {
        addTag(text, mChildViews.size());
    }

    /**
     * Inserts the specified TagView into this ContainerLayout at the specified location.
     * The TagView is inserted before the current element at the specified location.
     *
     * @param text     the string
     * @param position the position
     */
    public void addTag(String text, int position) {
        onAddTag(text, position);
        postInvalidate();
    }

    /**
     * Remove a TagView in specified position.
     *
     * @param position position of the tag
     */
    public void removeTag(int position) {
        onRemoveTag(position);
        postInvalidate();
    }

    /**
     * Remove all TagViews.
     */
    public void removeAllTags() {
        mChildViews.clear();
        removeAllViews();
        postInvalidate();
    }

    /**
     * Set OnTagClickListener for TagView.
     *
     * @param listener the listener
     */
    public void setOnTagClickListener(TagView.OnTagClickListener listener) {
        mOnTagClickListener = listener;
    }

    /**
     * Get TagView text.
     *
     * @param position the position
     * @return the string to be returned
     */
    public String getTagText(int position) {
        return ((TagView) mChildViews.get(position)).getText();
    }

    /**
     * Get a string list for all tags in TagContainerLayout.
     *
     * @return the list in string
     */
    public List<String> getTags() {
        List<String> tmpList = new ArrayList<String>();
        for (View view : mChildViews) {
            if (view instanceof TagView) {
                tmpList.add(((TagView) view).getText());
            }
        }
        return tmpList;
    }

    /**
     * Set whether the child view can be dragged.
     *
     * @param enable enable to true
     */
    public final void setDragEnable(boolean enable) {
        this.mDragEnable = enable;
    }

    /**
     * Get current view is drag enable attribute.
     *
     * @return true or false
     */
    public final boolean getDragEnable() {
        return mDragEnable;
    }

    /**
     * Set vertical interval
     *
     * @param interval float for interval
     */
    public final void setVerticalInterval(float interval) {
        mVerticalInterval = (int) dp2px(getContext(), interval);
        postInvalidate();
    }

    /**
     * Get vertical interval in this view.
     *
     * @return height unit in hi
     */
    public int getVerticalInterval() {
        return mVerticalInterval;
    }

    /**
     * Set horizontal interval.
     *
     * @param interval width unit for horizontal
     * @return TagContainerLayout
     */
    public TagContainerLayout setHorizontalInterval(float interval) {
        mHorizontalInterval = (int) dp2px(getContext(), interval);
        postInvalidate();
        return this;
    }

    /**
     * Get horizontal interval in this view.
     *
     * @return get the unit of width
     */
    public int getHorizontalInterval() {
        return mHorizontalInterval;
    }

    /**
     * Get TagContainerLayout border width.
     *
     * @return get the unit of the border
     */
    public float getBorderWidth() {
        return mBorderWidth;
    }

    /**
     * Set TagContainerLayout border width.
     *
     * @param width get the border of the width unit
     * @return TagContainerLayout
     */
    public TagContainerLayout setBorderWidth(float width) {
        this.mBorderWidth = width;
        return this;
    }

    /**
     * Get TagContainerLayout border radius.
     *
     * @return as it is
     */
    public float getBorderRadius() {
        return mBorderRadius;
    }

    /**
     * Set TagContainerLayout border radius.
     *
     * @param radius as it is
     * @return TagContainerLayout
     */
    public TagContainerLayout setBorderRadius(float radius) {
        this.mBorderRadius = radius;
        return this;
    }

    /**
     * Get TagContainerLayout border color.
     *
     * @return as it is
     */
    public int getBorderColor() {
        return mBorderColor;
    }

    /**
     * Set TagContainerLayout border color.
     *
     * @param color as it is
     * @return TagContainerLayout
     */
    public TagContainerLayout setBorderColor(int color) {
        this.mBorderColor = color;
        return this;
    }

    /**
     * Get TagContainerLayout background color.
     *
     * @return as it is
     */
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    /**
     * Set TagContainerLayout background color.
     *
     * @param color as it is
     */
    public void setBackgroundColor(int color) {
        this.mBackgroundColor = color;
    }

    /**
     * Get container layout gravity.
     *
     * @return as it is
     */
    public int getGravity() {
        return mGravity;
    }

    /**
     * Set container layout gravity.
     *
     * @param gravity as it is
     * @return TagContainerLayout
     */
    public TagContainerLayout setGravity(int gravity) {
        this.mGravity = gravity;
        return this;
    }

    /**
     * Get TagContainerLayout ViewDragHelper sensitivity.
     *
     * @return as it is
     */
    public float getSensitivity() {
        return mSensitivity;
    }

    /**
     * Set TagContainerLayout ViewDragHelper sensitivity.
     *
     * @param sensitivity as it is
     * @return TagContainerLayout
     */
    public TagContainerLayout setSensitivity(float sensitivity) {
        this.mSensitivity = sensitivity;
        return this;
    }


    /**
     * Set the TagView text max length (must be more or big than 3).
     *
     * @param maxLength the max length of the text inside each tagvie.
     * @return TagContainerLayout
     */
    public TagContainerLayout setTagMaxLength(int maxLength) {
        mTagMaxLength = maxLength < TAG_MIN_LENGTH ? TAG_MIN_LENGTH : maxLength;
        return this;
    }

    /**
     * Set the application mode for this
     *
     * @param mode mode code
     * @return TagContainerLayout
     */
    public final TagContainerLayout setMode(LayouMode mode) {
        mMode = mode;
        for (int i = 0; i < mChildViews.size(); i++) {
            if (mChildViews.get(i) instanceof TagView) {
                TagView d = (TagView) mChildViews.get(i);
                d.setMode(mode);
                if (mode == LayouMode.SINGLE_CHOICE || mode == LayouMode.MULTIPLE_CHOICE) {
                    d.setIsViewClickable(true);
                }
            }
        }
        return this;
    }

    public final TagContainerLayout setPreselectedTags(int[] list) {
        mPreselectedTags = list;
        return this;
    }

    public final TagContainerLayout setPreselectedTags(int[] list, int theme_on_reselected) {
        mPreselectedTags = list;
        mThemeOnPreselected = onUpdateColorFactory(theme_on_reselected);
        return this;
    }

    /**
     * Get TagView max length.
     *
     * @return as it is
     */
    public int getTagMaxLength() {
        return mTagMaxLength;
    }

    /**
     * Set TagView to use this theme when the click is active
     *
     * @param theme as it is
     */
    public void setThemeOnActive(int theme) {
        mThemeActive = theme;
        profile_active = onUpdateColorFactory(theme);
    }

    /**
     * Set TagView theme.
     *
     * @param theme as it is
     */
    public void setTheme(int theme) {
        mTheme = theme;
        profile_normal = onUpdateColorFactory(theme);
    }

    /**
     * Get TagView theme.
     *
     * @return as it is
     */
    public int getTheme() {
        return mTheme;
    }

    /**
     * Get TagView is clickable.
     *
     * @return as it is
     */
    public boolean getIsTagViewClickable() {
        return isTagViewClickable;
    }

    /**
     * Set TagView is clickable
     *
     * @param clickable as it is
     */
    public void setIsTagViewClickable(boolean clickable) {
        this.isTagViewClickable = clickable;
    }

    /**
     * Get TagView border width.
     *
     * @return as it is
     */
    public float getTagBorderWidth() {
        return mTagBorderWidth;
    }

    /**
     * Set TagView border width.
     *
     * @param width as it is
     */
    public void setTagBorderWidth(float width) {
        this.mTagBorderWidth = width;
    }

    /**
     * Get TagView border radius.
     *
     * @return as it is
     */
    public float getTagBorderRadius() {
        return mTagBorderRadius;
    }

    /**
     * Set TagView border radius.
     *
     * @param radius as it is
     */
    public void setTagBorderRadius(float radius) {
        this.mTagBorderRadius = radius;
    }

    /**
     * Get TagView text size.
     *
     * @return as it is
     */
    public float getTagTextSize() {
        return mTagTextSize;
    }

    /**
     * Set TagView text size.
     *
     * @param size as it is
     */
    public void setTagTextSize(float size) {
        this.mTagTextSize = size;
    }

    /**
     * Get TagView horizontal padding.
     *
     * @return as it is
     */
    public int getTagHorizontalPadding() {
        return mTagHorizontalPadding;
    }

    /**
     * Set TagView horizontal padding.
     *
     * @param padding as it is
     */
    public void setTagHorizontalPadding(int padding) {
        int ceilWidth = ceilTagBorderWidth();
        this.mTagHorizontalPadding = padding < ceilWidth ? ceilWidth : padding;
    }

    /**
     * Get TagView vertical padding.
     *
     * @return as it is
     */
    public int getTagVerticalPadding() {
        return mTagVerticalPadding;
    }

    /**
     * Set TagView vertical padding.
     *
     * @param padding as it is
     */
    public void setTagVerticalPadding(int padding) {
        int ceilWidth = ceilTagBorderWidth();
        this.mTagVerticalPadding = padding < ceilWidth ? ceilWidth : padding;
    }

    /**
     * Get TagView border color.
     *
     * @return as it is
     */
    public int getTagBorderColor() {
        return mTagBorderColor;
    }

    /**
     * Set TagView border color.
     *
     * @param color as it is
     */
    public void setTagBorderColor(int color) {
        this.mTagBorderColor = color;
    }

    /**
     * Get TagView background color.
     *
     * @return as it is
     */
    public int getTagBackgroundColor() {
        return mTagBackgroundColor;
    }

    /**
     * Set TagView background color.
     *
     * @param color as it is
     */
    public void setTagBackgroundColor(int color) {
        this.mTagBackgroundColor = color;
    }

    /**
     * Get TagView text color.
     *
     * @return as it is
     */
    public int getTagTextColor() {
        return mTagTextColor;
    }

    /**
     * Set tag text direction, support:View.TEXT_DIRECTION_RTL AND  View.TEXT_DIRECTION_LTR,
     * default View.TEXT_DIRECTION_LTR
     *
     * @param textDirection as it is DIRECITON
     */
    public void setTagTextDirection(int textDirection) {
        this.mTagTextDirection = textDirection;
    }

    /**
     * Get TagView typeface.
     *
     * @return as it is
     */
    public Typeface getTagTypeface() {
        return mTagTypeface;
    }

    /**
     * Set TagView typeface.
     *
     * @param typeface as it is
     */
    public void setTagTypeface(String typeface) {
        this.mTagTypeface = helper.getTypface(typeface, mConx);
    }

    /**
     * Get tag text direction
     *
     * @return as it is
     */
    public int getTagTextDirection() {
        return mTagTextDirection;
    }

    /**
     * Set TagView text color.
     *
     * @param color as it is
     */
    public void setTagTextColor(int color) {
        this.mTagTextColor = color;
    }

    public float dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public float sp2px(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public ArrayList<String> getSelectedItems() {
        ArrayList<String> lit = new ArrayList<>();
        for (int i = 0; i < mChildViews.size(); i++) {
            if (mChildViews.get(i) instanceof TagView) {
                TagView d = (TagView) mChildViews.get(i);
                if (d.isFlag_on()) {
                    lit.add(d.getText());
                }
            }
        }
        return lit;
    }

    public ArrayList<Integer> getSelectedOrders() {
        ArrayList<Integer> lit = new ArrayList<>();
        for (int i = 0; i < mChildViews.size(); i++) {
            if (mChildViews.get(i) instanceof TagView) {
                TagView d = (TagView) mChildViews.get(i);
                if (d.isFlag_on()) {
                    lit.add(i);
                }
            }
        }
        return lit;
    }
}
