package com.itllp.tipOnDiscount.persistence.impl.test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.test.mock.MockContext;

import com.itllp.tipOnDiscount.persistence.Persister;
import com.itllp.tipOnDiscount.persistence.impl.PreferencesFilePersister;

import junit.framework.TestCase;


class StubSharedPreferencesEditor implements SharedPreferences.Editor {

	private String stub_lastBooleanKey = null;
	private boolean stub_lastBooleanValue = Boolean.FALSE;
	private String stub_lastIntKey = null;
	private int stub_lastIntValue = Integer.MIN_VALUE;
	private String stub_lastStringKey = null;
	private String stub_lastStringValue = null;
	private boolean stub_wasClearCalled = false;
	private boolean stub_wasCommitCalled = false;
	
	@Override
	public void apply() {
	}

	@Override
	public Editor clear() {
		stub_wasClearCalled = true;
		return this;
	}

	@Override
	public boolean commit() {
		stub_wasCommitCalled = true;
		return true;
	}

	@Override
	public Editor putBoolean(String key, boolean value) {
		stub_lastBooleanKey = key;
		stub_lastBooleanValue = value;
		return this;
	}

	public String stub_getLastBooleanKey() {
		return stub_lastBooleanKey;
	}
	
	public boolean stub_getLastBooleanValue() {
		return stub_lastBooleanValue;
	}
	
	@Override
	public Editor putFloat(String arg0, float arg1) {
		return null;
	}

	@Override
	public Editor putInt(String key, int value) {
		stub_lastIntKey = key;
		stub_lastIntValue = value;
		return this;
	}

	public String stub_getLastIntKey() {
		return stub_lastIntKey;
	}
	
	public int stub_getLastIntValue() {
		return stub_lastIntValue;
	}
	
	@Override
	public Editor putLong(String arg0, long arg1) {
		return null;
	}

	@Override
	public Editor putString(String key, String value) {
		stub_lastStringKey = key;
		stub_lastStringValue = value;
		return this;
	}

	public String stub_getLastStringKey() {
		return stub_lastStringKey;
	}
	
	public String stub_getLastStringValue() {
		return stub_lastStringValue;
	}
	
	@Override
	public Editor putStringSet(String arg0, Set<String> arg1) {
		return null;
	}

	@Override
	public Editor remove(String arg0) {
		return null;
	}

	public boolean stub_wasClearCalled() {
		return stub_wasClearCalled;
	}
	
	public boolean stub_wasCommitCalled() {
		return stub_wasCommitCalled;
	}
	
}


class StubSharedPreferences implements SharedPreferences {
	private Map<String, String> stub_stringMap = new HashMap<String, String>();
	private Map<String, Integer> stub_intMap = new HashMap<String, Integer>();
	private Map<String, Boolean> stub_boolMap = new HashMap<String, Boolean>();
	private StubSharedPreferencesEditor stubSharedPreferencesEditor = null;
	
	@Override
	public boolean contains(String key) {
		if (stub_stringMap.containsKey(key)) {
			return true;
		}
		if (stub_intMap.containsKey(key)) {
			return true;
		}
		if (stub_boolMap.containsKey(key)) {
			return true;
		}
		return false;
	}

	@Override
	public Editor edit() {
		stubSharedPreferencesEditor = new StubSharedPreferencesEditor();
		return stubSharedPreferencesEditor;
	}

	public Editor stub_getEditor() {
		return stubSharedPreferencesEditor;
	}
	
	@Override
	public Map<String, ?> getAll() {
		return null;
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		Boolean value = stub_boolMap.get(key);
		if (null == value) {
			value = defaultValue;
		}
		return value;
	}

	public void stub_setBoolean(String key, boolean value) {
		Boolean booleanValue = Boolean.valueOf(value);
		stub_boolMap.put(key, booleanValue);
	}
	
	@Override
	public float getFloat(String arg0, float arg1) {
		return 0;
	}

	public void stub_setInt(String key, int value) {
		Integer integerValue = Integer.valueOf(value);
		stub_intMap.put(key, integerValue);
	}
	
	@Override
	public int getInt(String key, int defaultValue) {
		Integer value = stub_intMap.get(key); 
		if (null == value) {
			value = defaultValue;
		}
		return value;
	}

	@Override
	public long getLong(String arg0, long arg1) {
		return 0;
	}

	
	public void stub_setString(String key, String value) {
		stub_stringMap.put(key, value);
	}
	
	@Override
	public String getString(String key, String defaultValue) {
		String value = stub_stringMap.get(key); 
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
	private Context stubContext;
	private StubSharedPreferences stubSharedPreferences;
	private String preferencesFileName;
	private Persister persister;
	private String expectedKey = "MyKey";
	private String missingKey = "No such key in preferences file";
	private Boolean expectedBooleanValue;
	private BigDecimal expectedBigDecimalValue;
	private String expectedStringValue;
	private int expectedIntValue = 42;
	private boolean expectedBoolValue = true;
	
	public PreferencesFilePersisterTests(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		preferencesFileName = "test-prefs";
		expectedBigDecimalValue = new BigDecimal("3.50");
		expectedBooleanValue = Boolean.TRUE;
		expectedStringValue = expectedBigDecimalValue.toPlainString();
		stubSharedPreferences = new StubSharedPreferences();
		stubContext = new MockContext() {
			@Override
			public
			SharedPreferences getSharedPreferences(String fileName, int mode) {
				return stubSharedPreferences;
			}
		};
		persister = new PreferencesFilePersister(preferencesFileName);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	public void testInitialState() {
		// Verify postconditions
		assertNull("Non-null editor", stubSharedPreferences.stub_getEditor());
	}
	
	
	public void testRetrieveBigDecimalWhenValueDoesNotExist() {
		// Call method under test
		BigDecimal value = persister.retrieveBigDecimal(stubContext, 
				missingKey);
		
		// Verify postconditions
		assertNull("Incorrect value", value);
	}

	
	public void testRetrieveBigDecimalWhenValueExists() {
		stubSharedPreferences.stub_setString(expectedKey, expectedStringValue);
		
		// Call method under test
		BigDecimal actualValue = persister.retrieveBigDecimal(stubContext, 
				expectedKey);
		
		// Verify postconditions
		assertEquals("Incorrect value", expectedBigDecimalValue, actualValue);
	}


	public void testRetrieveBooleanWhenValueDoesNotExist() {
		// Call method under test
		Boolean value = persister.retrieveBoolean(stubContext, missingKey);
		
		// Verify postconditions
		assertNull("Incorrect value", value);
	}

	
	public void testRetrieveBooleanWhenValueExists() {
		Boolean expectedBooleanValue = Boolean.valueOf(expectedBoolValue);
		stubSharedPreferences.stub_setBoolean(expectedKey, expectedBoolValue);
		
		// Call method under test
		Boolean actualValue = persister.retrieveBoolean(stubContext, expectedKey);
		
		// Verify postconditions
		assertEquals("Incorrect value", expectedBooleanValue, actualValue);
	}


	public void testRetrieveIntegerWhenValueDoesNotExist() {
		// Call method under test
		Integer value = persister.retrieveInteger(stubContext, missingKey);
		
		// Verify postconditions
		assertNull("Incorrect value", value);
	}

	
	public void testRetrieveIntegerWhenValueExists() {
		Integer expectedIntegerValue = Integer.valueOf(expectedIntValue);
		stubSharedPreferences.stub_setInt(expectedKey, expectedIntValue);
		
		// Call method under test
		Integer actualValue = persister.retrieveInteger(stubContext, expectedKey);
		
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

	
	public void testSaveBooleanWhenBeginSaveNotCalled() {
		// Call method under test and verify postconditions
		try {
			persister.save(expectedKey, expectedBooleanValue);
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
		persister.beginSave(stubContext);
		
		// Call method under test
		try {
			persister.save(null, expectedBigDecimalValue);
			fail("Should throw exception");
		} catch(Exception e) {}
	}

	
	public void testSaveBooleanWithNullKey() {
		// Set up preconditions
		persister.beginSave(stubContext);
		
		// Call method under test
		try {
			persister.save(null, expectedBooleanValue);
			fail("Should throw exception");
		} catch(Exception e) {}
	}

	
	public void testSaveIntWithNullKey() {
		// Set up preconditions
		persister.beginSave(stubContext);
		
		// Call method under test
		try {
			persister.save(null, expectedIntValue);
			fail("Should throw exception");
		} catch(Exception e) {}
	}

	
	public void testSaveNullBigDecimalValue() {
		// Set up preconditions
		persister.beginSave(stubContext);
		
		// Call method under test
		try {
			persister.save(expectedKey, (BigDecimal)null);
			fail("Should throw exception");
		} catch(Exception e) {}
	}
	
	
	public void testBeginSave() {
		// Call method under test
		persister.beginSave(stubContext);
		
		// Verify postconditions
		StubSharedPreferencesEditor stubEditor = (StubSharedPreferencesEditor)
				stubSharedPreferences.stub_getEditor();
		assertNotNull("Null editor", stubEditor);
		assertTrue("Clear was not called", stubEditor.stub_wasClearCalled());
	}

	
	public void testSaveBigDecimal() {
		// Set up preconditions
		persister.beginSave(stubContext);
		
		// Call method under test
		try {
			persister.save(expectedKey, expectedBigDecimalValue);
		} catch (Exception e) {
			fail("Threw exception");
		}
		
		// Verify postconditions
		StubSharedPreferencesEditor stubEditor = (StubSharedPreferencesEditor)
				stubSharedPreferences.stub_getEditor();
		assertEquals("Wrong key", expectedKey, stubEditor.stub_getLastStringKey());
		assertEquals("Wrong value", expectedStringValue,
				stubEditor.stub_getLastStringValue());
	}


	public void testSaveBoolean() {
		// Set up preconditions
		persister.beginSave(stubContext);
		
		// Call method under test
		try {
			persister.save(expectedKey, expectedBooleanValue);
		} catch (Exception e) {
			fail("Threw exception");
		}
		
		// Verify postconditions
		StubSharedPreferencesEditor stubEditor = (StubSharedPreferencesEditor)
				stubSharedPreferences.stub_getEditor();
		assertEquals("Wrong key", expectedKey, 
				stubEditor.stub_getLastBooleanKey());
		assertEquals("Wrong value", expectedBoolValue,
				stubEditor.stub_getLastBooleanValue());
	}


	public void testSaveInt() {
		// Set up preconditions
		persister.beginSave(stubContext);
		
		// Call method under test
		try {
			persister.save(expectedKey, expectedIntValue);
		} catch (Exception e) {
			fail("Threw exception");
		}
		
		// Verify postconditions
		StubSharedPreferencesEditor stubEditor = (StubSharedPreferencesEditor)
				stubSharedPreferences.stub_getEditor();
		assertEquals("Wrong key", expectedKey, stubEditor.stub_getLastIntKey());
		assertEquals("Wrong value", expectedIntValue,
				stubEditor.stub_getLastIntValue());
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
		persister.beginSave(stubContext);
		StubSharedPreferencesEditor editor = (StubSharedPreferencesEditor) 
				stubSharedPreferences.stub_getEditor();
		
		// Call method under test and verify postconditions
		try {
			persister.endSave();
		} catch (Exception e) {
			fail("Threw exception");
		}
		
		// Verify postconditions
		assertTrue("Commit was not called", editor.stub_wasCommitCalled());
		try {
			persister.endSave();
			fail("Did not throw exception");
		} catch (Exception e) {}
	}

	
}
