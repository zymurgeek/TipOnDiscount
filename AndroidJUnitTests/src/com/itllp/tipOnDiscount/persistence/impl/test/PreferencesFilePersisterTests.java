package com.itllp.tipOnDiscount.persistence.impl.test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.test.mock.MockContext;
import com.itllp.tipOnDiscount.persistence.impl.PreferencesFilePersister;

import junit.framework.TestCase;


class MockSharedPreferencesEditor implements SharedPreferences.Editor {

	private String lastStringKey = null;
	private String lastStringValue = null;
	private String lastIntKey = null;
	private int lastIntValue = Integer.MIN_VALUE;
	private boolean wasCommitCalled = false;
	
	@Override
	public void apply() {
	}

	@Override
	public Editor clear() {
		return null;
	}

	@Override
	public boolean commit() {
		wasCommitCalled = true;
		return true;
	}

	@Override
	public Editor putBoolean(String arg0, boolean arg1) {
		return null;
	}

	@Override
	public Editor putFloat(String arg0, float arg1) {
		return null;
	}

	@Override
	public Editor putInt(String key, int value) {
		lastIntKey = key;
		lastIntValue = value;
		return this;
	}

	public String mock_getLastIntKey() {
		return lastIntKey;
	}
	
	public int mock_getLastIntValue() {
		return lastIntValue;
	}
	
	@Override
	public Editor putLong(String arg0, long arg1) {
		return null;
	}

	@Override
	public Editor putString(String key, String value) {
		lastStringKey = key;
		lastStringValue = value;
		return this;
	}

	public String mock_getLastStringKey() {
		return lastStringKey;
	}
	
	public String mock_getLastStringValue() {
		return lastStringValue;
	}
	
	@Override
	public Editor putStringSet(String arg0, Set<String> arg1) {
		return null;
	}

	@Override
	public Editor remove(String arg0) {
		return null;
	}

	public boolean mock_wasCommitCalled() {
		return wasCommitCalled;
	}
	
}


class MockSharedPreferences implements SharedPreferences {
	private Map<String, String> mock_stringMap = new HashMap<String, String>();
	private Map<String, Integer> mock_intMap = new HashMap<String, Integer>();
	private MockSharedPreferencesEditor mockSharedPreferencesEditor = null;
	
	@Override
	public boolean contains(String key) {
		if (mock_stringMap.containsKey(key)) {
			return true;
		}
		if (mock_intMap.containsKey(key)) {
			return true;
		}
		return false;
	}

	@Override
	public Editor edit() {
		mockSharedPreferencesEditor = new MockSharedPreferencesEditor();
		return mockSharedPreferencesEditor;
	}

	public Editor mock_getEditor() {
		return mockSharedPreferencesEditor;
	}
	
	@Override
	public Map<String, ?> getAll() {
		return null;
	}

	@Override
	public boolean getBoolean(String arg0, boolean arg1) {
		return false;
	}

	@Override
	public float getFloat(String arg0, float arg1) {
		return 0;
	}

	public void mock_setInt(String key, int value) {
		Integer integerValue = Integer.valueOf(value);
		mock_intMap.put(key, integerValue);
	}
	
	@Override
	public int getInt(String key, int defaultValue) {
		Integer value = mock_intMap.get(key); 
		if (null == value) {
			value = defaultValue;
		}
		return value;
	}

	@Override
	public long getLong(String arg0, long arg1) {
		return 0;
	}

	
	public void mock_setString(String key, String value) {
		mock_stringMap.put(key, value);
	}
	
	@Override
	public String getString(String key, String defaultValue) {
		String value = mock_stringMap.get(key); 
		if (null == value) {
			value = defaultValue;
		}
		return value;
	}

	@Override
	public Set<String> getStringSet(String arg0, Set<String> arg1) {
		return null;
	}

	@Override
	public void registerOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener arg0) {
	}

	@Override
	public void unregisterOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener arg0) {
	}
}

public class PreferencesFilePersisterTests extends TestCase {
	private Context mockContext;
	private MockSharedPreferences mockSharedPreferences;
	private String preferencesFileName;
	private PreferencesFilePersister persister;
	private String expectedKey = "MyKey";
	private String missingKey = "No such key in preferences file";
	private BigDecimal expectedBigDecimalValue;
	private String expectedStringValue;
	private int expectedIntValue = 42;
	
	public PreferencesFilePersisterTests(String name) {
		super(name);
	}

	protected static void setUpBeforeClass() throws Exception {
	}

	protected static void tearDownAfterClass() throws Exception {
	}

	protected void setUp() throws Exception {
		super.setUp();
		preferencesFileName = "test-prefs";
		expectedBigDecimalValue = new BigDecimal("3.50");
		expectedStringValue = expectedBigDecimalValue.toPlainString();
		mockSharedPreferences = new MockSharedPreferences();
		mockContext = new MockContext() {
			@Override
			public
			SharedPreferences getSharedPreferences(String fileName, int mode) {
				return mockSharedPreferences;
			}
		};
		persister = new PreferencesFilePersister(preferencesFileName);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	public void testInitialState() {
		// Verify postconditions
		assertNull("Non-null editor", mockSharedPreferences.mock_getEditor());
	}
	
	
	public void testRetrieveBigDecimalWhenValueDoesNotExist() {
		// Call method under test
		BigDecimal value = persister.retrieveBigDecimal(mockContext, 
				missingKey);
		
		// Verify postconditions
		assertNull("Incorrect value", value);
	}

	
	public void testRetrieveBigDecimalWhenValueExists() {
		mockSharedPreferences.mock_setString(expectedKey, expectedStringValue);
		
		// Call method under test
		BigDecimal actualValue = persister.retrieveBigDecimal(mockContext, 
				expectedKey);
		
		// Verify postconditions
		assertEquals("Incorrect value", expectedBigDecimalValue, actualValue);
	}


	public void testRetrieveIntegerWhenValueDoesNotExist() {
		// Call method under test
		Integer value = persister.retrieveInteger(mockContext, missingKey);
		
		// Verify postconditions
		assertNull("Incorrect value", value);
	}

	
	public void testRetrieveIntegerWhenValueExists() {
		Integer expectedIntegerValue = Integer.valueOf(expectedIntValue);
		mockSharedPreferences.mock_setInt(expectedKey, expectedIntValue);
		
		// Call method under test
		Integer actualValue = persister.retrieveInteger(mockContext, expectedKey);
		
		// Verify postconditions
		assertEquals("Incorrect value", expectedIntegerValue, actualValue);
	}


	public void testSaveBigDecimalWhenBeginSaveNotCalled() {
		// Call method under test and verify postconditions
		try {
			persister.save(expectedKey, expectedBigDecimalValue);
			fail("Should throw exception");
		} catch(Exception e) {}
	}

	
	public void testSaveIntWhenBeginSaveNotCalled() {
		// Call method under test and verify postconditions
		try {
			persister.save(expectedKey, expectedIntValue);
			fail("Should throw exception");
		} catch(Exception e) {}
	}

	
	public void testSaveBigDecimalWithNullKey() {
		// Set up preconditions
		persister.beginSave(mockContext);
		
		// Call method under test
		try {
			persister.save(null, expectedBigDecimalValue);
			fail("Should throw exception");
		} catch(Exception e) {}
	}

	
	public void testSaveIntWithNullKey() {
		// Set up preconditions
		persister.beginSave(mockContext);
		
		// Call method under test
		try {
			persister.save(null, expectedIntValue);
			fail("Should throw exception");
		} catch(Exception e) {}
	}

	
	public void testSaveNullValue() {
		// Set up preconditions
		persister.beginSave(mockContext);
		
		// Call method under test
		try {
			persister.save(expectedKey, null);
			fail("Should throw exception");
		} catch(Exception e) {}
	}
	
	
	public void testBeginSave() {
		// Call method under test
		persister.beginSave(mockContext);
		
		// Verify postconditions
		assertNotNull("Null editor", mockSharedPreferences.mock_getEditor());
	}

	
	public void testSaveBigDecimal() {
		// Set up preconditions
		persister.beginSave(mockContext);
		
		// Call method under test
		try {
			persister.save(expectedKey, expectedBigDecimalValue);
		} catch (Exception e) {
			fail("Threw exception");
		}
		
		// Verify postconditions
		MockSharedPreferencesEditor mockEditor = (MockSharedPreferencesEditor)
				mockSharedPreferences.mock_getEditor();
		assertEquals("Wrong key", expectedKey, mockEditor.mock_getLastStringKey());
		assertEquals("Wrong value", expectedStringValue,
				mockEditor.mock_getLastStringValue());
	}


	public void testSaveInt() {
		// Set up preconditions
		persister.beginSave(mockContext);
		
		// Call method under test
		try {
			persister.save(expectedKey, expectedIntValue);
		} catch (Exception e) {
			fail("Threw exception");
		}
		
		// Verify postconditions
		MockSharedPreferencesEditor mockEditor = (MockSharedPreferencesEditor)
				mockSharedPreferences.mock_getEditor();
		assertEquals("Wrong key", expectedKey, mockEditor.mock_getLastIntKey());
		assertEquals("Wrong value", expectedIntValue,
				mockEditor.mock_getLastIntValue());
	}


	public void testEndSaveWithoutBeginSave() {
		// Call method under test and verify postconditions
		try {
			persister.endSave();
			fail("Did not throw exception");
		} catch (Exception e) {}
	}

	
	public void testEndSaveAfterBeginSave() {
		// Set up preconditions
		persister.beginSave(mockContext);
		MockSharedPreferencesEditor editor = (MockSharedPreferencesEditor) 
				mockSharedPreferences.mock_getEditor();
		
		// Call method under test and verify postconditions
		try {
			persister.endSave();
		} catch (Exception e) {
			fail("Threw exception");
		}
		
		// Verify postconditions
		assertTrue("Commit was not called", editor.mock_wasCommitCalled());
		try {
			persister.endSave();
			fail("Did not throw exception");
		} catch (Exception e) {}
	}

	
	//TODO finish tests
}
