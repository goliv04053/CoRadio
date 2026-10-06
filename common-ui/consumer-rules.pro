# Dialogs are instantiated by class name (BaseDialogFragment.newInstance uses
# Class.forName(...).getConstructor()), and the framework re-creates fragments by name
# after process death, so keep the names and public no-arg constructors.
-keep class * extends app.coradio.shared.view.dialog.BaseDialogFragment {
    <init>();
}
