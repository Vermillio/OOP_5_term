package CylinderPlaneSim;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.DirectionalLight;
import com.jme3.light.AmbientLight;
import com.jme3.math.Vector3f;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import java.util.Vector;


public class Simulation extends SimpleApplication {

    public static void main(String args[]) {
        Simulation app = new Simulation();
        app.start();
    }

    BulletAppState bulletAppState;

    Material cylinder_mat;
    Material plane_mat;

    Cylinder            cylinder;
    Box                         plane;
    Vector<RigidBodyControl>    cylinders_physic;
    RigidBodyControl            plane_physic;
    ActionListener              action_listener; 

    public Simulation() {
        cylinder = new Cylinder(50, 40, 1.f, 5.f, true, false); 
        cylinders_physic = new Vector<>();
        plane = new Box(60f, 0.1f, 10f);
        plane.scaleTextureCoordinates(new Vector2f(3, 18));
        action_listener = new ActionListener() {
            public void onAction(String name, boolean keyPressed, float tpf) 
            {
                if (name.equals("add_cylinder") && !keyPressed) 
                {
                    initCylinder();
                }
            }
        };
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(1,0,-2).normalizeLocal());
        rootNode.addLight(sun);

        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White);
        rootNode.addLight(al);

        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        SSAOFilter ssaoFilter = new SSAOFilter(12.94f, 43.92f, 0.33f, 0.61f);
        fpp.addFilter(ssaoFilter);
        viewPort.addProcessor(fpp);

        cam.setLocation(new Vector3f(-4,6,22));

        inputManager.addMapping("add_cylinder", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(action_listener, "add_cylinder");

        initMaterials();
        initPlane();
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        if (cylinders_physic.isEmpty())
            return;
        for (RigidBodyControl c : cylinders_physic) {
            if (c.getPhysicsLocation().y < -400)
                bulletAppState.getPhysicsSpace().remove(c);
        }
    }
  
    public void initMaterials() {
        cylinder_mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        cylinder_mat.setBoolean("UseMaterialColors",true);
        cylinder_mat.setColor("Diffuse",ColorRGBA.Green);
        cylinder_mat.setColor("Specular",ColorRGBA.White);
        cylinder_mat.setFloat("Shininess", 500f);  

        plane_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key = new TextureKey("Textures/Concrete.jpg");
        key.setGenerateMips(true);
        Texture tex = assetManager.loadTexture(key);
        tex.setWrap(WrapMode.Repeat);
        plane_mat.setTexture("ColorMap", tex);
    }
  
    public void initPlane() {
        Geometry plane_geo = new Geometry("Plane", plane);
        plane_geo.setMaterial(plane_mat);
        // Положение
        plane_geo.setLocalTranslation(0, -1.1f, 0);
        this.rootNode.attachChild(plane_geo);
        Quaternion angle = new Quaternion();
        // Угол
        angle.fromAngleAxis(FastMath.PI/6, new Vector3f(0,0,-1));
        plane_geo.setLocalRotation(angle);
        //Физика
        plane_physic = new RigidBodyControl(0.0f);
        plane_geo.addControl(plane_physic);
        bulletAppState.getPhysicsSpace().add(plane_physic);
    }

    public void initCylinder() {
        Geometry cyl_geo = new Geometry("cyl", cylinder);
        cyl_geo.setMaterial(cylinder_mat);
        rootNode.attachChild(cyl_geo);
        // Положение
        cyl_geo.setLocalTranslation(new Vector3f(-20.f, 25.f, 0.f));
        // Физика
        cylinders_physic.add(new RigidBodyControl(3f));
        cyl_geo.addControl(cylinders_physic.lastElement());
        bulletAppState.getPhysicsSpace().add(cylinders_physic.lastElement());
    }    
}