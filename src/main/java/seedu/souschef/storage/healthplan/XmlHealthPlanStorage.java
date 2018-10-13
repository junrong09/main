package seedu.souschef.storage.healthplan;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.exceptions.DataConversionException;
import seedu.souschef.commons.exceptions.IllegalValueException;
import seedu.souschef.commons.util.FileUtil;
import seedu.souschef.model.AppContent;
import seedu.souschef.model.ReadOnlyAppContent;
import seedu.souschef.storage.XmlFeatureStorage;
import seedu.souschef.storage.XmlFileStorage;
import seedu.souschef.storage.XmlSerializableGeneric;

/**
 * A class to access AppContent data stored as an xml file on the hard disk.
 */
public class XmlHealthPlanStorage extends XmlFeatureStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlHealthPlanStorage.class);

    public XmlHealthPlanStorage(Path filePath) {
        super(filePath);
    }


    public XmlHealthPlanStorage(Path filePath, AppContent appContent) {
        super(filePath, appContent);
    }

    public Optional<ReadOnlyAppContent> readFeature() throws DataConversionException,
            FileNotFoundException {
        return readFeature(this.filePath);
    }

    /**
     * Similar to {@link #readFeature()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyAppContent> readFeature(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("AppContent file " + filePath + " not found");
            return Optional.empty();
        }
        XmlSerializableGeneric xmlhealthplanBook = new XmlSerializableHealthPlan();
        xmlhealthplanBook = XmlFileStorage.loadDataFromSaveFile(filePath, "healthplan");
        try {
            return Optional.of(xmlhealthplanBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }


    /**
     * Similar to {@link #saveFeature(ReadOnlyAppContent)}
     * @param filePath location of the data. Cannot be null
     */
    @Override
    public void saveFeature(ReadOnlyAppContent appContent, Path filePath) throws IOException {
        requireNonNull(appContent);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableHealthPlan(appContent));
    }







}