package ai.konduit.serving.basic;

import ai.konduit.serving.config.SchemaType;
import ai.konduit.serving.model.PythonConfig;
import ai.konduit.serving.pipeline.PythonPipelineStep;
import ai.konduit.serving.util.python.PythonVariables;
import org.datavec.api.writable.Writable;
import org.nd4j.linalg.io.ClassPathResource;

public class BasicConfigurationPython {
    public static void main(String[] args) throws Exception {
        String pythonPath = "C:/SKIL/miniconda/envs/py37/python37.zip;C:/SKIL/miniconda/envs/py37/DLLs;" +
                "C:/SKIL/miniconda/envs/py37/lib;C:/SKIL/miniconda/envs/py37;" +
                "C:/Users/shams/AppData/Roaming/Python/Python37/site-packages;" +
                "C:/SKIL/miniconda/envs/py37/lib/site-packages;" +
                "C:/SKIL/miniconda/envs/py37/lib/site-packages/win32;" +
                "C:/SKIL/miniconda/envs/py37/lib/site-packages/win32/lib;" +
                "C:/SKIL/miniconda/envs/py37/lib/site-packages/Pythonwin";

        String pythonCodePath = new ClassPathResource("scripts/loadimage.py").getFile().getAbsolutePath();
        String imagePath =  new ClassPathResource("images/COCO_train2014_000000000009.jpg").getFile().getAbsolutePath();

        PythonConfig pythonConfig = PythonConfig.builder()
                .pythonPath(pythonPath)
                .pythonCodePath(pythonCodePath)
                .pythonInput("x", PythonVariables.Type.STR.name())
                .pythonOutput("y", PythonVariables.Type.STR.name())
                .pythonOutput("y2", PythonVariables.Type.NDARRAY.name())
                .build();

        PythonPipelineStep pythonPipelineStep = new PythonPipelineStep().step("default", pythonConfig,
                new String[] {"x"}, new SchemaType[] {SchemaType.String},
                new String[] {"y", "y2"}, new SchemaType[] {SchemaType.String, SchemaType.NDArray});

        Writable[][] output = pythonPipelineStep.getRunner().transform(imagePath);

        System.out.println(output[0][0]);
        System.out.println(output[0][1]);
    }
}
