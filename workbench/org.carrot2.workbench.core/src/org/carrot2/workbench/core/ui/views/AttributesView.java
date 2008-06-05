package org.carrot2.workbench.core.ui.views;

import java.util.Map;

import org.carrot2.workbench.core.ui.attributes.AttributeListComponent;
import org.carrot2.workbench.core.ui.attributes.AttributesSynchronizer;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.*;
import org.eclipse.ui.part.*;

/**
 * View showing attribute values for active editor.
 * 
 * It works similar to Outline view: every time new editor is activated, and it is
 * {@link IAdaptable} and it adapts to {@link AttributesProvider}, new page in this view
 * is created using provider's {@link AttributesProvider#getBindableDescriptor()} as a
 * source of attributes to display. If attributes are groupped, than appropriate groups
 * are displayed.
 */
public class AttributesView extends PageBookView
{

    public static final String ID = "org.carrot2.workbench.core.attributes";

    @Override
    protected IPage createDefaultPage(PageBook book)
    {
        MessagePage defaultPage = new MessagePage();
        initPage(defaultPage);
        defaultPage.createControl(book);
        defaultPage.setMessage("Nothing to show for this editor");
        return defaultPage;
    }

    @Override
    protected PageRec doCreatePage(IWorkbenchPart part)
    {
        if (!(part instanceof IAdaptable))
        {
            return null;
        }
        final AttributeListComponent provider =
            (AttributeListComponent) ((IAdaptable) part)
                .getAdapter(AttributeListComponent.class);
        if (provider == null)
        {
            return null;
        }
        Page page = new Page()
        {

            private AttributeListComponent component;

            @Override
            public void createControl(Composite parent)
            {
                component = new AttributeListComponent();
                component.init(parent, provider.getBindableDescriptor());
                for (Map.Entry<String, Object> entry : provider.getAttributeValues()
                    .entrySet())
                {
                    component.setAttributeValue(entry.getKey(), entry.getValue());
                }
                AttributesSynchronizer.synchronize(provider, component);
            }

            @Override
            public Control getControl()
            {
                return component.getControl();
            }

            @Override
            public void setFocus()
            {
            }

            @Override
            public void setActionBars(IActionBars actionBars)
            {
                component.populateToolbar(actionBars.getToolBarManager());
            }

            @Override
            public void dispose()
            {
                component.dispose();
                super.dispose();
            }

        };
        initPage(page);
        page.createControl(getPageBook());
        return new PageRec(part, page);
    }

    @Override
    protected void doDestroyPage(IWorkbenchPart part, PageRec pageRecord)
    {
        ((Page) pageRecord.page).dispose();
        pageRecord.dispose();
    }

    @Override
    protected IWorkbenchPart getBootstrapPart()
    {
        return this.getSite().getPage().getActiveEditor();
    }

    @Override
    protected boolean isImportant(IWorkbenchPart part)
    {
        return (part instanceof IEditorPart);
    }

}
