package ru.vseopecheni.app.data.db.migration;


import io.realm.DynamicRealm;
import io.realm.RealmSchema;

public class RealmMigration implements io.realm.RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            schema.create("Person")
                    .addField("name", String.class)
                    .addField("age", int.class);
            oldVersion++;
        }
    }

}
