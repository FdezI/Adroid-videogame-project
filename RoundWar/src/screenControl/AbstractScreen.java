package screenControl;

import roundwar.RoundWar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

/**
 * @author sagoc dev
 * Clase base para la mayoría de las pantallas
 */
public abstract class AbstractScreen implements Screen {
	protected final RoundWar game;     
	protected BitmapFont font; 
    //protected final SpriteBatch batch; 
    protected Stage stage;
    protected SpriteBatch batch;
    protected Skin skin;
    protected Table table;
    
    protected Texture tbg;
    protected Image bg;
    
	
	/**
     * Constructor
     */
	public AbstractScreen( RoundWar game ) {
        this.game = game;                  
        // Stage viewport with 0x0px size
        this.stage = new Stage( 0, 0, true );
        batch = stage.getSpriteBatch();
        // The stage receive the input events
        Gdx.input.setInputProcessor(this.stage);
	}
	
	/**
	 * Return the font
	 * @return
	 */
    public BitmapFont getFont() { 
        if( font == null ) { 
            font = new BitmapFont(); 
        } 
        return font; 
    }
 
    /**
     * Return the skin
     * @return
     */
    protected Skin getSkin() { 
        if( skin == null ) {
            FileHandle skinFile = Gdx.files.internal("skin/uiskin.json"); 
            skin = new Skin(skinFile); 
        } 
        return skin; 
    }
 
    /**
     * Return the table
     * @return
     */
    protected Table getTable() { 
        if( table == null ) { 
            table = new Table( getSkin() ); 
            table.setFillParent( true ); 
            table.debug(); 
            
            stage.addActor( table ); 
        } 
        return table; 
    }
	   
    @Override
    public void show() {
    }

	/**
	 * 
	 */
	@Override
	public void resize(int width, int height) {
        // Resize stage viewport
		stage.setViewport( width, height, true );
	}

	/**
	 * 
	 */
	@Override
	public void render(float delta) {
        // Update actors
        stage.act(delta);
                  
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f ); 
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
		
		// Draw actors
		stage.draw();
	}
	
	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override 
	public void resume() {
	}

	@Override
	public void dispose() {
		batch.dispose();
        //stage.dispose();
        if (font != null)
                font.dispose();
	}

	protected String getName() {
		return getClass().getSimpleName();
	}
	
	protected void setBackground(String image){
		// Inicialize background
		tbg = new Texture(Gdx.files.internal(image));
		
		tbg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        TextureRegionDrawable bgRegion = new TextureRegionDrawable(new TextureRegion(tbg,512,512));
        bg = new Image(bgRegion, Scaling.stretch);
        bg.setFillParent(true);
        
	    // Add background
		stage.addActor(bg);
	}

}
