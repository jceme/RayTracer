Ray Tracer
==========

**Ray Tracer** is one of my former university projects for *Computer Graphics*.

My task was to implement a ray tracer using **Constructive Solid Geometry** (CSG).

The concept of CSG allows clear mathematically-correct rendered scenes containing base objects (box, sphere, cone, ...) put together using CSG operations (intersection, difference, ...).

Preparation
-----------
Check-out this project and in the project root execute `mvn clean package`

This will build a JAR archive containing the **GUI version**.

To build the command line **batch version** execute `mvn -P batch clean package`

GUI version
-----------
After building the project you can start the program like so: `java -jar target/raytracer-1.0.0-jar-with-dependencies.jar`

The GUI will start, rendering the configured scene (defaults to *SimpleRedSphere*). To render a different scene just pass the sole scene name as program argument.
The available scenes are shown below.

Batch version
-------------
You start the batch version of **Ray Tracer** like so: `java -jar target/raytracer-batch-1.0.0-jar-with-dependencies.jar IMAGEWIDTH IMAGEHEIGHT SCENENAME...`

This will render each provided scene in the given image dimension as *SCENENAME.png* in the current directory.

Available scenes
----------------
The available scenes from *de.raytracing.raytracer.scenes* are:

* **ComplexMirrorScene**   
This scene shows an interior setup with mirroring spheres and a pillar and a half-transparent blob.
It demonstrates mirror and transparency effects.
A red cone located behind the camera is shown only by mirror surfaces to prove scene completeness.

* **ConeScene**  
This scene shows a sole gray-scale cone demonstrating anti-aliasing and light/shadowing effects.

* **FadingSpotScene**  
This is the same scene setup as in *SpotScene* but with strong fading spot lights.

* **ModifierScene**  
This scene demonstrates the modifiers available like rotation or translation.
The cylinder and box are yellow, the floor is white, the scene is lit by green and red light.

* **PillarScene**  
This shows an interior scene, a long room equipped with six pillars.
It should demonstrate how to construct complex objects (the pillar) and re-use it multiple times.
The lighting is not very well though.

* **SimpleRedSphere**  
This is a very simple setup just showing a sole red sphere. The scene demonstrates the anti-aliasing as well as light/shadowing effects.

* **SimpleRedSphereUnfiltered**  
This is the same scene as *SimpleRedSphere* but not using anti-aliasing showing artifacts at the sphere border.

* **SpotScene**  
This scene shows a white sphere lying on a white floor.
The scene is lit by two spot lights in the color green and red pointing from left resp. right side at the sphere.

* **SpotSceneUnfiltered**  
This is the same scene as *SpotScene* but not using anti-aliasing showing clear artifacts at the sphere and spot light borders.

* **UnionScene**  
This scene demonstrates the CSG operators intersection (top left), group (top right) and union (bottom) using half-transparent spheres.
Especially the difference is shown between group which just groups objects and union which removes inner borders.
The union (bottom object) has different shadows due to the missing inner sphere borders.
