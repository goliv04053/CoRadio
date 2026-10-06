# RadioStation / RadioStationToAdd travel through Bundles as java.io.Serializable.
-keepclassmembers class app.coradio.shared.model.media.RadioStation { *; }
-keepclassmembers class app.coradio.shared.model.media.RadioStationToAdd { *; }
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
