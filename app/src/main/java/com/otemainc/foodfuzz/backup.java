package com.otemainc.foodfuzz;

import android.app.backup.BackupAgent;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.os.ParcelFileDescriptor;

import java.io.IOException;

public class backup extends BackupAgent {
    /**
     * The application is being asked to write any data changed since the last
     * time it performed a backup operation. The state data recorded during the
     * last backup pass is provided in the <code>oldState</code> file
     * descriptor. If <code>oldState</code> is <code>null</code>, no old state
     * is available and the application should perform a full backup. In both
     * cases, a representation of the final backup state after this pass should
     * be written to the file pointed to by the file descriptor wrapped in
     * <code>newState</code>.
     * <p>
     * Each entity written to the {@link BackupDataOutput}
     * <code>data</code> stream will be transmitted
     * over the current backup transport and stored in the remote data set under
     * the key supplied as part of the entity.  Writing an entity with a negative
     * data size instructs the transport to delete whatever entity currently exists
     * under that key from the remote data set.
     *
     * @param oldState An open, read-only ParcelFileDescriptor pointing to the
     *                 last backup state provided by the application. May be
     *                 <code>null</code>, in which case no prior state is being
     *                 provided and the application should perform a full backup.
     * @param data     A structured wrapper around an open, read/write
     *                 file descriptor pointing to the backup data destination.
     *                 Typically the application will use backup helper classes to
     *                 write to this file.
     * @param newState An open, read/write ParcelFileDescriptor pointing to an
     *                 empty file. The application should record the final backup
     *                 state here after writing the requested data to the <code>data</code>
     */
    @Override
    public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data, ParcelFileDescriptor newState) throws IOException {

    }

    /**
     * The application is being restored from backup and should replace any
     * existing data with the contents of the backup. The backup data is
     * provided through the <code>data</code> parameter. Once
     * the restore is finished, the application should write a representation of
     * the final state to the <code>newState</code> file descriptor.
     * <p>
     * The application is responsible for properly erasing its old data and
     * replacing it with the data supplied to this method. No "clear user data"
     * operation will be performed automatically by the operating system. The
     * exception to this is in the case of a failed restore attempt: if
     * onRestore() throws an exception, the OS will assume that the
     * application's data may now be in an incoherent state, and will clear it
     * before proceeding.
     *
     * @param data           A structured wrapper around an open, read-only
     *                       file descriptor pointing to a full snapshot of the
     *                       application's data.  The application should consume every
     *                       entity represented in this data stream.
     * @param appVersionCode The value of the <a
     *                       href="{@docRoot}guide/topics/manifest/manifest-element.html#vcode">{@code
     *                       android:versionCode}</a> manifest attribute,
     *                       from the application that backed up this particular data set. This
     *                       makes it possible for an application's agent to distinguish among any
     *                       possible older data versions when asked to perform the restore
     *                       operation.
     * @param newState       An open, read/write ParcelFileDescriptor pointing to an
     *                       empty file. The application should record the final backup
     *                       state here after restoring its data from the <code>data</code> stream.
     */
    @Override
    public void onRestore(BackupDataInput data, int appVersionCode, ParcelFileDescriptor newState) throws IOException {

    }
}
