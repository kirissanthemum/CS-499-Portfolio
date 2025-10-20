///////////////////////////////////////////////////////////////////////////////
// scenemanager.cpp
// ============
// manage the preparing and rendering of 3D scenes - textures, materials, lighting
//
//  AUTHOR: Brian Battersby - SNHU Instructor / Computer Science
//	Created for CS-330-Computational Graphics and Visualization, Nov. 1st, 2023
///////////////////////////////////////////////////////////////////////////////
#define GLM_ENABLE_EXPERIMENTAL

#include "SceneManager.h"

#ifndef STB_IMAGE_IMPLEMENTATION
#define STB_IMAGE_IMPLEMENTATION
#include "stb_image.h"
#endif

#include <glm/gtx/transform.hpp>

// declaration of global variables
namespace
{
	const char* g_ModelName = "model";
	const char* g_ColorValueName = "objectColor";
	const char* g_TextureValueName = "objectTexture";
	const char* g_UseTextureName = "bUseTexture";
	const char* g_UseLightingName = "bUseLighting";
}

/***********************************************************
 *  SceneManager()
 *
 *  The constructor for the class
 ***********************************************************/
SceneManager::SceneManager(ShaderManager *pShaderManager)
{
	m_pShaderManager = pShaderManager;
	m_basicMeshes = new ShapeMeshes();
}

/***********************************************************
 *  ~SceneManager()
 *
 *  The destructor for the class
 ***********************************************************/
SceneManager::~SceneManager()
{
	m_pShaderManager = NULL;
	delete m_basicMeshes;
	m_basicMeshes = NULL;
}

/***********************************************************
 *  CreateGLTexture()
 *
 *  This method is used for loading textures from image files,
 *  configuring the texture mapping parameters in OpenGL,
 *  generating the mipmaps, and loading the read texture into
 *  the next available texture slot in memory.
 ***********************************************************/
bool SceneManager::CreateGLTexture(const char* filename, std::string tag)
{
	int width = 0;
	int height = 0;
	int colorChannels = 0;
	GLuint textureID = 0;

	// indicate to always flip images vertically when loaded
	stbi_set_flip_vertically_on_load(true);

	// try to parse the image data from the specified image file
	unsigned char* image = stbi_load(
		filename,
		&width,
		&height,
		&colorChannels,
		0);

	// if the image was successfully read from the image file
	if (image)
	{
		std::cout << "Successfully loaded image:" << filename << ", width:" << width << ", height:" << height << ", channels:" << colorChannels << std::endl;

		glGenTextures(1, &textureID);
		glBindTexture(GL_TEXTURE_2D, textureID);

		// set the texture wrapping parameters
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		// set texture filtering parameters
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		// if the loaded image is in RGB format
		if (colorChannels == 3)
			glTexImage2D(GL_TEXTURE_2D, 0, GL_SRGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, image);
		else if (colorChannels == 4)
			glTexImage2D(GL_TEXTURE_2D, 0, GL_SRGB_ALPHA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
		else
		{
			std::cout << "Not implemented to handle image with " << colorChannels << " channels" << std::endl;
			return false;
		}

		// generate the texture mipmaps for mapping textures to lower resolutions
		glGenerateMipmap(GL_TEXTURE_2D);

		// free the image data from local memory
		stbi_image_free(image);
		glBindTexture(GL_TEXTURE_2D, 0); // Unbind the texture

		// register the loaded texture and associate it with the special tag string
		m_textureIDs[m_loadedTextures].ID = textureID;
		m_textureIDs[m_loadedTextures].tag = tag;
		m_loadedTextures++;

		return true;
	}

	std::cout << "Could not load image:" << filename << std::endl;

	// Error loading the image
	return false;
}

/***********************************************************
 *  BindGLTextures()
 *
 *  This method is used for binding the loaded textures to
 *  OpenGL texture memory slots.  There are up to 16 slots.
 ***********************************************************/
void SceneManager::BindGLTextures()
{
	for (int i = 0; i < m_loadedTextures; i++)
	{
		// bind textures on corresponding texture units
		glActiveTexture(GL_TEXTURE0 + i);
		glBindTexture(GL_TEXTURE_2D, m_textureIDs[i].ID);
	}
}

/***********************************************************
 *  DestroyGLTextures()
 *
 *  This method is used for freeing the memory in all the
 *  used texture memory slots.
 ***********************************************************/
void SceneManager::DestroyGLTextures()
{
	for (int i = 0; i < m_loadedTextures; i++)
	{
		glDeleteTextures(1, &m_textureIDs[i].ID);
	}
}

/***********************************************************
 *  FindTextureID()
 *
 *  This method is used for getting an ID for the previously
 *  loaded texture bitmap associated with the passed in tag.
 ***********************************************************/
int SceneManager::FindTextureID(std::string tag)
{
	int textureID = -1;
	int index = 0;
	bool bFound = false;

	while ((index < m_loadedTextures) && (bFound == false))
	{
		if (m_textureIDs[index].tag.compare(tag) == 0)
		{
			textureID = m_textureIDs[index].ID;
			bFound = true;
		}
		else
			index++;
	}

	return(textureID);
}

/***********************************************************
 *  FindTextureSlot()
 *
 *  This method is used for getting a slot index for the previously
 *  loaded texture bitmap associated with the passed in tag.
 ***********************************************************/
int SceneManager::FindTextureSlot(std::string tag)
{
	int textureSlot = -1;
	int index = 0;
	bool bFound = false;

	while ((index < m_loadedTextures) && (bFound == false))
	{
		if (m_textureIDs[index].tag.compare(tag) == 0)
		{
			textureSlot = index;
			bFound = true;
		}
		else
			index++;
	}

	return(textureSlot);
}

/***********************************************************
 *  FindMaterial()
 *
 *  This method is used for getting a material from the previously
 *  defined materials list that is associated with the passed in tag.
 ***********************************************************/
bool SceneManager::FindMaterial(std::string tag, OBJECT_MATERIAL& material)
{
	if (m_objectMaterials.size() == 0)
	{
		return(false);
	}

	int index = 0;
	bool bFound = false;
	while ((index < m_objectMaterials.size()) && (bFound == false))
	{
		if (m_objectMaterials[index].tag.compare(tag) == 0)
		{
			bFound = true;
			material.diffuseColor = m_objectMaterials[index].diffuseColor;
			material.specularColor = m_objectMaterials[index].specularColor;
			material.shininess = m_objectMaterials[index].shininess;
		}
		else
		{
			index++;
		}
	}

	return(true);
}

/***********************************************************
 *  SetTransformations()
 *
 *  This method is used for setting the transform buffer
 *  using the passed in transformation values.
 ***********************************************************/
void SceneManager::SetTransformations(
	glm::vec3 scaleXYZ,
	float XrotationDegrees,
	float YrotationDegrees,
	float ZrotationDegrees,
	glm::vec3 positionXYZ)
{
	// variables for this method
	glm::mat4 modelView;
	glm::mat4 scale;
	glm::mat4 rotationX;
	glm::mat4 rotationY;
	glm::mat4 rotationZ;
	glm::mat4 translation;

	// set the scale value in the transform buffer
	scale = glm::scale(scaleXYZ);
	// set the rotation values in the transform buffer
	rotationX = glm::rotate(glm::radians(XrotationDegrees), glm::vec3(1.0f, 0.0f, 0.0f));
	rotationY = glm::rotate(glm::radians(YrotationDegrees), glm::vec3(0.0f, 1.0f, 0.0f));
	rotationZ = glm::rotate(glm::radians(ZrotationDegrees), glm::vec3(0.0f, 0.0f, 1.0f));
	// set the translation value in the transform buffer
	translation = glm::translate(positionXYZ);

	modelView = translation * rotationZ * rotationY * rotationX * scale;

	if (NULL != m_pShaderManager)
	{
		m_pShaderManager->setMat4Value(g_ModelName, modelView);
	}
}

/***********************************************************
 *  SetShaderColor()
 *
 *  This method is used for setting the passed in color
 *  into the shader for the next draw command
 ***********************************************************/
void SceneManager::SetShaderColor(
	float redColorValue,
	float greenColorValue,
	float blueColorValue,
	float alphaValue)
{
	// variables for this method
	glm::vec4 currentColor;

	currentColor.r = redColorValue;
	currentColor.g = greenColorValue;
	currentColor.b = blueColorValue;
	currentColor.a = alphaValue;

	if (NULL != m_pShaderManager)
	{
		m_pShaderManager->setIntValue(g_UseTextureName, false);
		m_pShaderManager->setVec4Value(g_ColorValueName, currentColor);
	}
}

/***********************************************************
 *  SetShaderTexture()
 *
 *  This method is used for setting the texture data
 *  associated with the passed in ID into the shader.
 ***********************************************************/
void SceneManager::SetShaderTexture(
	std::string textureTag)
{
	if (NULL != m_pShaderManager)
	{
		m_pShaderManager->setIntValue(g_UseTextureName, true);

		int textureID = -1;
		textureID = FindTextureSlot(textureTag);
		m_pShaderManager->setSampler2DValue(g_TextureValueName, textureID);
	}
}

/***********************************************************
 *  SetTextureUVScale()
 *
 *  This method is used for setting the texture UV scale
 *  values into the shader.
 ***********************************************************/
void SceneManager::SetTextureUVScale(float u, float v)
{
	if (NULL != m_pShaderManager)
	{
		m_pShaderManager->setVec2Value("UVscale", glm::vec2(u, v));
	}
}

/***********************************************************
 *  SetShaderMaterial()
 *
 *  This method is used for passing the material values
 *  into the shader.
 ***********************************************************/
void SceneManager::SetShaderMaterial(
	std::string materialTag)
{
	if (m_objectMaterials.size() > 0)
	{
		OBJECT_MATERIAL material;
		bool bReturn = false;

		bReturn = FindMaterial(materialTag, material);
		if (bReturn == true)
		{
			m_pShaderManager->setVec3Value("material.diffuseColor", material.diffuseColor);
			m_pShaderManager->setVec3Value("material.specularColor", material.specularColor);
			m_pShaderManager->setFloatValue("material.shininess", material.shininess);
		}
	}
}

/**************************************************************/
/*** STUDENTS CAN MODIFY the code in the methods BELOW for  ***/
/*** preparing and rendering their own 3D replicated scenes.***/
/*** Please refer to the code in the OpenGL sample project  ***/
/*** for assistance.                                        ***/
/**************************************************************/

//LoadSceneTextures()
void SceneManager::LoadSceneTextures()
{
	bool bReturn = false;
	
	bReturn = CreateGLTexture(
		"textures/matteblack.jpg", //added in texture file
		"polishcap");

	bReturn = CreateGLTexture(
		"textures/polishcolor.jpg",
		"bottle");

	bReturn = CreateGLTexture(
		"textures/woodplane.jpg",
		"bottomplane");

	bReturn = CreateGLTexture(
		"textures/background2.jpg",
		"background");

	bReturn = CreateGLTexture(
		"textures/gold foil.jpg",
		"gold");

	bReturn = CreateGLTexture(
		"textures/redknit.jpg",
		"knit");

	bReturn = CreateGLTexture(
		"textures/blackjar.jpg",
		"jar");

	bReturn = CreateGLTexture(
		"textures/candletop.jpg",
		"lid");

	bReturn = CreateGLTexture(
		"textures/golddeco.jpg",
		"cover");

	//loaded textures need to be bound to texture slots
	BindGLTextures();
}
/***********************************************************
 *  DefineObjectMaterials()
 *
 *  This method is used for configuring the various material
 *  settings for all of the objects within the 3D scene.
 ***********************************************************/
void SceneManager::DefineObjectMaterials()
{
	OBJECT_MATERIAL glassMaterial;
	glassMaterial.diffuseColor = glm::vec3(0.9f, 0.9f, 0.9f);
	glassMaterial.specularColor = glm::vec3(1.0f, 1.0f, 1.0f);
	glassMaterial.shininess = 95.0;
	glassMaterial.tag = "glass";
	// Glass remains unchanged
	m_objectMaterials.push_back(glassMaterial);

	OBJECT_MATERIAL paintMaterial;
	paintMaterial.diffuseColor = glm::vec3(0.6f, 0.4f, 0.3f); //Brighter diffuse
	paintMaterial.specularColor = glm::vec3(0.2f, 0.2f, 0.2f); //Added specular
	paintMaterial.shininess = 50.0;
	paintMaterial.tag = "paint";
	m_objectMaterials.push_back(paintMaterial);

	OBJECT_MATERIAL woodMaterial;
	woodMaterial.diffuseColor = glm::vec3(0.6f, 0.4f, 0.3f);
	woodMaterial.specularColor = glm::vec3(0.2f, 0.2f, 0.2f); //Added specular
	woodMaterial.shininess = 90.0;
	woodMaterial.tag = "wood";
	m_objectMaterials.push_back(woodMaterial);

	OBJECT_MATERIAL matteMaterial;
	matteMaterial.diffuseColor = glm::vec3(0.6f, 0.4f, 0.3f);
	matteMaterial.specularColor = glm::vec3(0.1f, 0.1f, 0.1f); //Boosted specular
	matteMaterial.shininess = 30.0;
	matteMaterial.tag = "matte";
	m_objectMaterials.push_back(matteMaterial);

}

/***********************************************************
 *  SetupSceneLights()
 *
 *  This method is called to add and configure the light
 *  sources for the 3D scene. There are up to 4 light sources.
 ***********************************************************/
void SceneManager::SetupSceneLights()
{
	m_pShaderManager->setBoolValue(g_UseLightingName, true);

	
	//point light 1 w/ color
	m_pShaderManager->setVec3Value("pointLights[0].position", -8.0f, 6.0f, 0.0f);
	m_pShaderManager->setVec3Value("pointLights[0].ambient", 0.5f, 0.3f, 0.2f); //color added for warmth of scene
	m_pShaderManager->setVec3Value("pointLights[0].diffuse", 0.4f, 0.4, 0.4f);
	m_pShaderManager->setVec3Value("pointLights[0].specular", 0.4f, 0.4f, 0.4f);
	m_pShaderManager->setBoolValue("pointLights[0].bActive", true);

	//point light 2
	m_pShaderManager->setVec3Value("pointLights[1].position", 8.0f, 6.0f, 0.0f);
	m_pShaderManager->setVec3Value("pointLights[1].ambient", 0.25, 0.25, 0.15); //a little color added but more of a warm white
	m_pShaderManager->setVec3Value("pointLights[1].diffuse", 0.9f, 0.9f, 0.9f);
	m_pShaderManager->setVec3Value("pointLights[1].specular", 0.9f, 0.9f, 0.9f);
	m_pShaderManager->setBoolValue("pointLights[1].bActive", true);

	//spot light
	m_pShaderManager->setVec3Value("spotLight.ambient", 0.9f, 0.9f, 0.9f);
	m_pShaderManager->setVec3Value("spotLight.diffuse", 1.0f, 1.0f, 1.0f);
	m_pShaderManager->setVec3Value("spotLight.specular", 0.7f, 0.7f, 0.7f);
	m_pShaderManager->setFloatValue("spotLight.constant", 1.0f);
	m_pShaderManager->setFloatValue("spotLight.linear", 0.09f);
	m_pShaderManager->setFloatValue("spotLight.quadratic", 0.032f);
	m_pShaderManager->setFloatValue("spotLight.cutOff", glm::cos(glm::radians(20.5f)));
	m_pShaderManager->setFloatValue("spotLight.outerCutOff", glm::cos(glm::radians(28.0f)));
	m_pShaderManager->setBoolValue("spotLight.bActive", true);

	m_pShaderManager->setBoolValue(g_UseLightingName, true);

	// Directional light
	m_pShaderManager->setVec3Value("directionalLight.direction", -0.05f, -0.3f, -0.1f);
	m_pShaderManager->setVec3Value("directionalLight.ambient", 0.7f, 0.7f, 0.7f);
	m_pShaderManager->setVec3Value("directionalLight.diffuse", 0.6f, 0.6f, 0.6f);
	m_pShaderManager->setVec3Value("directionalLight.specular", 0.2f, 0.2f, 0.2f); // Added specular
	m_pShaderManager->setBoolValue("directionalLight.bActive", true);

	// Point lights and spotlight remain unchanged

}

/***********************************************************
 *  PrepareScene()
 *
 *  This method is used for preparing the 3D scene by loading
 *  the shapes, textures in memory to support the 3D scene 
 *  rendering
 ***********************************************************/
void SceneManager::PrepareScene()
{
	//load the texures for the 3D scene
	LoadSceneTextures();
	//define material used for objects in 3D scene
	DefineObjectMaterials();
	//add and define the light sources for the 3D scene
	SetupSceneLights();
	// only one instance of a particular mesh needs to be
	// loaded in memory no matter how many times it is drawn
	// in the rendered 3D scene

	m_basicMeshes->LoadConeMesh(); //not used
	m_basicMeshes->LoadPrismMesh(); //not used
	m_basicMeshes->LoadPyramid4Mesh(); //not used
	m_basicMeshes->LoadSphereMesh(); //not used
	m_basicMeshes->LoadTaperedCylinderMesh(); //not used
	m_basicMeshes->LoadTorusMesh();  //torus for hair tie
	m_basicMeshes->LoadPlaneMesh(); //planes bottom and top
	m_basicMeshes->LoadBoxMesh(); //box for nail polish bottle
	m_basicMeshes->LoadCylinderMesh(); //cylinder for nail polish cap
}

/***********************************************************
 *  RenderScene()
 *
 *  This method is used for rendering the 3D scene by 
 *  transforming and drawing the basic 3D shapes
 ***********************************************************/
void SceneManager::RenderScene()
{
	// declare the variables for the transformations
	glm::vec3 scaleXYZ;
	float XrotationDegrees = 0.0f;
	float YrotationDegrees = 0.0f;
	float ZrotationDegrees = 0.0f;
	glm::vec3 positionXYZ;

	/*** Set needed transformations before drawing the basic mesh.  ***/
	/*** This same ordering of code should be used for transforming ***/
	/*** and drawing all the basic 3D shapes.						***/
	/******************************************************************/
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(20.0f, 1.0f, 10.0f); //size of plane

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(0.0f, 0.0f, 0.0f); //position set at default zero

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	//SetShaderColor(0.737, 0.561, 0, 0.561); //plane on bottom
	SetShaderTexture("bottomplane"); //calls imported wood texture to match source image
	SetShaderMaterial("wood"); //needs a texture with semi shininess
	m_pShaderManager->setIntValue(g_UseTextureName, true);  
	m_pShaderManager->setIntValue(g_UseLightingName, true);
	// draw the mesh with transformation values
	m_basicMeshes->DrawPlaneMesh(); //initiate shape
	/****************************************************************/

	//complex 3d shape which is bottle of nail polish
	//box shape of nail polish bottle
	scaleXYZ = glm::vec3(2.0f, 2.0f, 2.0f); //size according to source image

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees =60.0f; //bottle is turned at an angle
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(-4.0f, 2.25f, 0.0f); //nail polish bottle is to the left of other objects in reference photo

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	//SetShaderColor(1.000, 0, 0, 1.000); //bottle color is red/orange
	SetShaderTexture("bottle"); //call imported sparkly red texture for polish
	SetShaderMaterial("glass"); //glass needs material with high shininess value
	m_pShaderManager->setIntValue(g_UseTextureName, true);
	m_pShaderManager->setIntValue(g_UseLightingName, true);
	// draw the mesh with transformation values
	m_basicMeshes->DrawBoxMesh(); //initiate shape

	//cylinder shape for nail polish cap
	scaleXYZ = glm::vec3(0.75f, 3.0f, 0.75f); //size accordingly to source image and polish bottle

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(-4.0f, 3.0f, 0.0f); //set cap position direcly on top of bottle

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	//SetShaderColor(0, 0, 0, 0);
	SetShaderTexture("polishcap"); //calls imported texture for polish cap
	SetShaderMaterial("glass"); //polish bottle has a shine in source image
	m_pShaderManager->setIntValue(g_UseTextureName, true);
	m_pShaderManager->setIntValue(g_UseLightingName, true);
	// draw the mesh with transformation values
	m_basicMeshes->DrawCylinderMesh(); //initiate shape

	//created second plane so that objects can be fully seen rendered
	//background plane
	scaleXYZ = glm::vec3(20.0f, 1.0f, 10.0f); //size similar to other plane

	// set the XYZ rotation for the mesh
	XrotationDegrees = 90.0f; //rotate plane shape on x plane to allow it to stand vertically
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(0.0f, 9.0f, -10.0f); //align w/ other plan but at the back of it

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	//SetShaderColor(1.000, 0.973, 1, 0.863);
	SetShaderTexture("background"); //calls texture imported
	SetShaderMaterial("paint"); //make semi shiny
	m_pShaderManager->setIntValue(g_UseTextureName, true);
	m_pShaderManager->setIntValue(g_UseLightingName, true);
	// draw the mesh with transformation values
	m_basicMeshes->DrawPlaneMesh(); //initiate shape

	//book bottom box
	scaleXYZ = glm::vec3(15.0f, 0.25f, 8.0f); //size according to source image

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(0.0f, 0.05f, 0.0f); //set on bottom

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	//SetShaderColor(0, 0, 0, 0);
	SetShaderTexture("polishcap"); //reuse polish cap texture as they are very similar
	SetShaderMaterial("matte"); //book is dull
	m_pShaderManager->setIntValue(g_UseTextureName, true);
	m_pShaderManager->setIntValue(g_UseLightingName, true);
	// draw the mesh with transformation values
	m_basicMeshes->DrawBoxMesh(); //initiate value

	//book contents box
	scaleXYZ = glm::vec3(14.5f, 1.0f, 7.0f); //line up with other box shape but a little smalled on z plane

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(0.0f, 0.50f, 0.0f); //set on top of bottom box shape

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	//SetShaderColor(0, 0, 0, 0);
	SetShaderTexture("gold"); //calls imported gold foil texture
	SetShaderMaterial("glass"); //need shiny material for foil effect
	m_pShaderManager->setIntValue(g_UseTextureName, true);
	m_pShaderManager->setIntValue(g_UseLightingName, true);
	// draw the mesh with transformation values
	m_basicMeshes->DrawBoxMesh(); //initiate shape

	//book top box
	scaleXYZ = glm::vec3(15.0f, 0.25f, 8.0f); //size to match other book box shapes

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(0.0f, 1.25f, 0.0f); //position on top of gold box shape

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	//SetShaderColor(0, 0, 0, 0);
	SetShaderTexture("polishcap"); //reuse polish cap texture as they are very similar
	SetShaderMaterial("matte"); //book is matte
	m_pShaderManager->setIntValue(g_UseTextureName, true);
	m_pShaderManager->setIntValue(g_UseLightingName, true);
	// draw the mesh with transformation values
	m_basicMeshes->DrawBoxMesh(); //initiate shape

	//hair tie torus
	scaleXYZ = glm::vec3(1.0f, 1.0f, 1.0f); //size down scrunchie as true to source image

	// set the XYZ rotation for the mesh
	XrotationDegrees = 85.0f; //alter angle on x plane to set torus flat along book
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(-1.25f, 1.6f, 3.0f); //position on top of book shapes

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	//SetShaderColor(0, 0, 0, 0);
	SetShaderTexture("knit"); //calls imported knit design for hait tie
	SetShaderMaterial("matte"); //hair tie needs dull texture
	m_pShaderManager->setIntValue(g_UseTextureName, true);
	m_pShaderManager->setIntValue(g_UseLightingName, true);
	// draw the mesh with transformation values
	m_basicMeshes->DrawTorusMesh(); //initiate shape

	//candle base
	scaleXYZ = glm::vec3(3.0f, 5.5f, 3.0f); //size candle jar as true to source image

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(2.0f, 1.25f, 0.0f); //set on top of book shapes

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	//SetShaderColor(0, 0, 0, 0);
	SetShaderTexture("jar"); //call texture for candle jar
	SetShaderMaterial("glass"); //candle jar needs shiny material
	m_pShaderManager->setIntValue(g_UseTextureName, true);
	m_pShaderManager->setIntValue(g_UseLightingName, true);
	// draw the mesh with transformation values
	m_basicMeshes->DrawCylinderMesh(); //initiate shape

	//candle lid cylinder
	scaleXYZ = glm::vec3(3.0f, 0.25f, 3.0f); //scale to same size as cylinder candle

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(2.0f, 6.75f, 0.0f); //set directly on top of cylinder

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	//SetShaderColor(0, 0, 0, 0);
	SetShaderTexture("lid"); //calls imported silver metal texture
	SetShaderMaterial("glass"); //candle lid need shiny finish
	m_pShaderManager->setIntValue(g_UseTextureName, true);
	m_pShaderManager->setIntValue(g_UseLightingName, true);
	// draw the mesh with transformation values
	m_basicMeshes->DrawCylinderMesh(); //initiate shape

	//book spine
	scaleXYZ = glm::vec3(15.0f, 0.25f, 1.0f); //line up with the other book box shapes

	// set the XYZ rotation for the mesh
	XrotationDegrees = 90.0f; //rotate along x plane to have box upright
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(0.0f, 0.75f, -4.0f); //postion to line up with back edge of book box shapes

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	//SetShaderColor(0, 0, 0, 0);
	SetShaderTexture("polishcap"); //book is matte so use same texture as polish cap
	SetShaderMaterial("matte"); //matte finish as book is dull in source image
	m_pShaderManager->setIntValue(g_UseTextureName, true);
	m_pShaderManager->setIntValue(g_UseLightingName, true);
	// draw the mesh with transformation values
	m_basicMeshes->DrawBoxMesh(); //initatite box shape

	//gold cover detail box shape
	scaleXYZ = glm::vec3(10.0f, 0.10f, 5.0f); //set position within space

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(0.0f, 1.35f, 0.0f); //set directly ontop of top cover box but below other objects

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	//SetShaderColor(0, 0, 0, 0);
	SetShaderTexture("cover"); //calls imported cover texture
	SetShaderMaterial("glass"); //use glass material for foil finish
	m_pShaderManager->setIntValue(g_UseTextureName, true);
	m_pShaderManager->setIntValue(g_UseLightingName, true);
	// draw the mesh with transformation values
	m_basicMeshes->DrawBoxMesh(); //initiate shape

	//CAPSTONE ADJUSTEMENTS
	//add another shape- Silver Bottle

	// Set scale
	scaleXYZ = glm::vec3(2.0f, 6.0f, 2.0f); // wide base, tall height

	// Set rotation (optional, upright bottle)
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// Set position slightly forward and to the side
	positionXYZ = glm::vec3(-5.0f, 0.5f, -2.0f); //

	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	//silver material
	SetShaderMaterial("glass"); //use glass material
	m_pShaderManager->setIntValue(g_UseTextureName, false);   // no texture, just color and lighting
	m_pShaderManager->setIntValue(g_UseLightingName, true);   // enable lighting

	// Draw the cylinder mesh
	m_basicMeshes->DrawCylinderMesh();

}
