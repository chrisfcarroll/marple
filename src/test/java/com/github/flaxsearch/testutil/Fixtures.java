package com.github.flaxsearch.testutil;

/*
 *   Copyright (c) 2016 Lemur Consulting Ltd.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class Fixtures {

    public static Directory openDirectory() {
        RAMDirectory directory = new RAMDirectory();
        populateIndex(directory);
        return directory;
    }

    private static void populateIndex(Directory directory) {
        try (IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig())) {

            {
                Document doc = new Document();
                doc.add(new StringField("field1", "value1", Field.Store.YES));
                doc.add(new TextField("field2", "here is some text", Field.Store.YES));
                writer.addDocument(doc);
                // more than one segment
                writer.commit();
            }

            {
                Document doc = new Document();
                doc.add(new StringField("field1", "value2", Field.Store.YES));
                doc.add(new TextField("field3", "this is some more text in a different field", Field.Store.YES));
                writer.addDocument(doc);
            }


        } catch (IOException e) {
            throw new RuntimeException("We're a RAMDirectory, this should never happen!");
        }
    }
}