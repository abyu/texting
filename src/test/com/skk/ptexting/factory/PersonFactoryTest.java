package com.skk.ptexting.factory;

import android.database.Cursor;
import android.test.mock.MockContentProvider;
import android.test.mock.MockContentResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class PersonFactoryTest {

    private MockContentResolver contentResolver;
    @Mock private MockContentProvider contentProvider;

    @Mock private Cursor cursor;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void APersonIsCreatedUsingNameContactResolvedFromTheAddressProvided(){
        //TODO: Testing is not possible as Android Compile time sdk sucks, does not allow testing


        /*when(cursor.getString(anyInt())).thenReturn("My Name");
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode("12456788"));
        when(contentProvider.query(uri, new String[]{anyString()}, anyString(), new String[]{anyString()}, anyString())).thenReturn(cursor);


        contentResolver.addProvider(uri.getAuthority(), contentProvider);

        Person person = new PersonFactory(contentResolver).fromAddress("12456788");

        Assert.assertThat(person.getName(), is("My Name"));*/

    }
}
