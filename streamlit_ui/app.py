import streamlit as st
import requests
import pandas as pd
import plotly.express as px
import plotly.graph_objects as go
from datetime import datetime, timedelta
import json

# Configuration
API_BASE_URL = "http://localhost:8000/api/v1"

# Page configuration
st.set_page_config(
    page_title="ATP_Re - Web Interface",
    page_icon="🚆",
    layout="wide",
    initial_sidebar_state="expanded"
)

# Custom CSS
st.markdown("""
<style>
    .main-header {
        font-size: 2.5rem;
        color: #1f77b4;
        text-align: center;
        margin-bottom: 2rem;
    }
    .metric-card {
        background-color: #f0f2f6;
        padding: 1rem;
        border-radius: 0.5rem;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }
</style>
""", unsafe_allow_html=True)

# Field descriptions for decoded packet data
FIELD_DESCRIPTIONS = {
    # Packet Header Fields
    "packet_no": "Packet Number/Type",
    "timestamp": "Recording Timestamp",
    "location": "Train Location (meters)",
    "speed": "Train Speed (km/h)",
    
    # MMI_DYNAMIC Fields
    "v_train": "Train Speed (km/h)",
    "a_train": "Train Acceleration (cm/s²)",
    "o_train": "Train Position (meters)",
    "o_brake_target": "Brake Target Position (meters)",
    "v_target": "Target Speed (km/h)",
    "t_interven_war": "Intervention Warning Time (seconds)",
    "v_permitted": "Permitted Speed (km/h)",
    "v_release": "Release Speed (km/h)",
    "v_intervention": "Intervention Speed (km/h)",
    "m_warning": "Warning Mode (0-15)",
    "m_slip": "Slip Indication (0-1)",
    "m_slide": "Slide Indication (0-1)",
    "o_bcsp": "BCSP Position (meters)",
    
    # MMI_STATUS Fields
    "m_adhesion": "Adhesion Mode",
    "m_mode": "Operating Mode",
    "m_level": "ATP Level",
    "m_emer_brake": "Emergency Brake Status",
    "m_service_brake": "Service Brake Status",
    "m_override_eoa": "Override EOA Status",
    "m_trip": "Trip Status",
    "m_active_cabin": "Active Cabin Identifier",
    
    # BTM Fields
    "sequence_number": "Telegram Sequence Number",
    "telegram_number": "Fragment Number (1-5)",
    "data_length": "Data Length (bytes)",
    "data_hex": "Raw Data (hexadecimal)",
    "nid_bg": "Balise Group Identifier",
    "m_count": "Message Count",
    
    # Other Fields
    "button": "Button Value",
    "status": "Status Value",
}

# Helper functions
def get_field_description(field_name):
    """Get human-readable description for a field name"""
    return FIELD_DESCRIPTIONS.get(field_name, field_name.replace("_", " ").title())


def get_tasks():
    """Fetch all tasks from API"""
    try:
        response = requests.get(f"{API_BASE_URL}/tasks/")
        response.raise_for_status()
        return response.json()
    except Exception as e:
        st.error(f"Error fetching tasks: {str(e)}")
        return []


def get_task_data(task_id, limit=100):
    """Fetch data for a specific task"""
    try:
        response = requests.get(f"{API_BASE_URL}/data/task/{task_id}?limit={limit}")
        response.raise_for_status()
        return response.json()
    except Exception as e:
        st.error(f"Error fetching task data: {str(e)}")
        return []


def get_task_events(task_id, limit=100):
    """Fetch events for a specific task"""
    try:
        response = requests.get(f"{API_BASE_URL}/events/task/{task_id}?limit={limit}")
        response.raise_for_status()
        return response.json()
    except Exception as e:
        st.error(f"Error fetching task events: {str(e)}")
        return []


def get_task_summary(task_id):
    """Fetch task summary report"""
    try:
        response = requests.get(f"{API_BASE_URL}/reports/task/{task_id}/summary")
        response.raise_for_status()
        return response.json()
    except Exception as e:
        st.error(f"Error fetching task summary: {str(e)}")
        return None


# Main application
def main():
    # Header
    st.markdown('<h1 class="main-header">🚆 ATP_Re - Automatic Train Protection System</h1>', 
                unsafe_allow_html=True)
    
    # Sidebar navigation
    st.sidebar.title("Navigation")
    page = st.sidebar.radio(
        "Go to",
        ["Dashboard", "File Upload", "Task Management", "Data Analysis", "Event Monitoring", "Reports"]
    )
    
    if page == "Dashboard":
        show_dashboard()
    elif page == "File Upload":
        show_file_upload()
    elif page == "Task Management":
        show_task_management()
    elif page == "Data Analysis":
        show_data_analysis()
    elif page == "Event Monitoring":
        show_event_monitoring()
    elif page == "Reports":
        show_reports()


def show_dashboard():
    """Dashboard page"""
    st.header("📊 Dashboard")
    
    # Fetch tasks
    tasks = get_tasks()
    
    if not tasks:
        st.info("No tasks available. Upload a file or create a task to get started.")
        return
    
    # Metrics
    col1, col2, col3, col4 = st.columns(4)
    
    with col1:
        st.metric("Total Tasks", len(tasks))
    
    with col2:
        pending = sum(1 for t in tasks if t['status'] == 'pending')
        st.metric("Pending Tasks", pending)
    
    with col3:
        completed = sum(1 for t in tasks if t['status'] == 'completed')
        st.metric("Completed Tasks", completed)
    
    with col4:
        failed = sum(1 for t in tasks if t['status'] == 'failed')
        st.metric("Failed Tasks", failed)
    
    st.divider()
    
    # Recent tasks
    st.subheader("Recent Tasks")
    df_tasks = pd.DataFrame(tasks)
    df_tasks['created_at'] = pd.to_datetime(df_tasks['created_at'])
    df_tasks = df_tasks.sort_values('created_at', ascending=False).head(10)
    st.dataframe(df_tasks[['id', 'task_name', 'task_type', 'status', 'created_at']], 
                 use_container_width=True)
    
    # Task status distribution
    st.subheader("Task Status Distribution")
    status_counts = df_tasks['status'].value_counts()
    fig = px.pie(values=status_counts.values, names=status_counts.index, 
                 title="Task Status Distribution")
    st.plotly_chart(fig, use_container_width=True)


def show_file_upload():
    """File upload page"""
    st.header("📁 File Upload")
    
    st.write("Upload ATP data files for processing")
    
    uploaded_file = st.file_uploader(
        "Choose a file",
        type=['dat', 'log', 'txt', 'bin', 'zip'],
        help="Supported formats: .dat, .log, .txt, .bin, .zip"
    )
    
    create_task = st.checkbox("Automatically create processing task", value=True)
    
    if uploaded_file is not None:
        st.write("File details:")
        st.write(f"- **Filename:** {uploaded_file.name}")
        st.write(f"- **File size:** {uploaded_file.size:,} bytes")
        
        if st.button("Upload", type="primary"):
            with st.spinner("Uploading file..."):
                try:
                    files = {'file': uploaded_file}
                    params = {'create_task': create_task}
                    response = requests.post(
                        f"{API_BASE_URL}/upload/",
                        files=files,
                        params=params
                    )
                    response.raise_for_status()
                    
                    result = response.json()
                    st.success(f"✅ File uploaded successfully! File ID: {result['id']}")
                    
                    if create_task:
                        st.info("A processing task has been automatically created.")
                    
                    st.json(result)
                
                except Exception as e:
                    st.error(f"❌ Upload failed: {str(e)}")


def show_task_management():
    """Task management page"""
    st.header("📋 Task Management")
    
    # Tabs for different views
    tab1, tab2 = st.tabs(["Task List", "Create Task"])
    
    with tab1:
        tasks = get_tasks()
        
        if not tasks:
            st.info("No tasks found.")
            return
        
        # Filter options
        col1, col2 = st.columns(2)
        with col1:
            status_filter = st.selectbox(
                "Filter by status",
                ["All", "pending", "processing", "completed", "failed"]
            )
        
        # Apply filter
        if status_filter != "All":
            tasks = [t for t in tasks if t['status'] == status_filter]
        
        # Display tasks
        for task in tasks:
            with st.expander(f"Task #{task['id']}: {task['task_name']}"):
                col1, col2 = st.columns(2)
                with col1:
                    st.write(f"**Type:** {task['task_type']}")
                    st.write(f"**Status:** {task['status']}")
                    st.write(f"**Created:** {task['created_at']}")
                with col2:
                    if task['description']:
                        st.write(f"**Description:** {task['description']}")
                    if task['file_path']:
                        st.write(f"**File:** {task['file_path']}")
                
                # Action buttons
                col1, col2, col3 = st.columns(3)
                with col1:
                    if st.button(f"View Details", key=f"view_{task['id']}"):
                        st.session_state.selected_task_id = task['id']
                        st.rerun()
    
    with tab2:
        st.subheader("Create New Task")
        
        task_name = st.text_input("Task Name")
        task_type = st.selectbox("Task Type", ["decode", "analyze", "report", "export"])
        description = st.text_area("Description")
        
        if st.button("Create Task", type="primary"):
            try:
                payload = {
                    "task_name": task_name,
                    "task_type": task_type,
                    "description": description
                }
                response = requests.post(f"{API_BASE_URL}/tasks/", json=payload)
                response.raise_for_status()
                
                st.success("✅ Task created successfully!")
                st.json(response.json())
            
            except Exception as e:
                st.error(f"❌ Task creation failed: {str(e)}")


def show_data_analysis():
    """Data analysis page"""
    st.header("📈 Data Analysis")
    
    tasks = get_tasks()
    if not tasks:
        st.info("No tasks available.")
        return
    
    # Task selection
    task_options = {f"Task #{t['id']}: {t['task_name']}": t['id'] for t in tasks}
    selected_task = st.selectbox("Select Task", list(task_options.keys()))
    task_id = task_options[selected_task]
    
    # Fetch data
    if st.button("Load Data", type="primary"):
        with st.spinner("Loading data..."):
            data = get_task_data(task_id, limit=1000)
            
            if not data:
                st.warning("No data found for this task.")
                return
            
            df = pd.DataFrame(data)
            df['timestamp'] = pd.to_datetime(df['timestamp'])
            
            st.success(f"Loaded {len(df)} data points")
            
            # Display statistics
            st.subheader("Data Statistics")
            col1, col2, col3, col4 = st.columns(4)
            
            with col1:
                st.metric("Data Points", len(df))
            with col2:
                if 'speed' in df.columns and df['speed'].notna().any():
                    st.metric("Avg Speed", f"{df['speed'].mean():.2f}")
            with col3:
                if 'speed' in df.columns and df['speed'].notna().any():
                    st.metric("Max Speed", f"{df['speed'].max():.2f}")
            with col4:
                time_range = df['timestamp'].max() - df['timestamp'].min()
                st.metric("Time Range", f"{time_range}")
            
            # Speed over time chart
            if 'speed' in df.columns and df['speed'].notna().any():
                st.subheader("Speed Over Time")
                fig = px.line(df, x='timestamp', y='speed', 
                             title='Train Speed Over Time')
                st.plotly_chart(fig, use_container_width=True)
            
            # Position over time chart
            if 'position' in df.columns and df['position'].notna().any():
                st.subheader("Position Over Time")
                fig = px.line(df, x='timestamp', y='position', 
                             title='Train Position Over Time')
                st.plotly_chart(fig, use_container_width=True)
            
            # Data table
            st.subheader("Data Table")
            st.dataframe(df, use_container_width=True)
            
            # Detailed decoded data view
            st.subheader("📦 Decoded Packet Details")
            st.info("Select a row to view detailed decoded packet values")
            
            # Add a row selector
            if len(df) > 0:
                row_index = st.number_input(
                    "Select row to view details (0-based index):",
                    min_value=0,
                    max_value=len(df)-1,
                    value=0,
                    step=1
                )
                
                # Get the selected row
                selected_row = df.iloc[row_index]
                
                # Display decoded data if available
                if 'decoded_data' in selected_row and selected_row['decoded_data']:
                    try:
                        # Parse JSON if it's a string
                        if isinstance(selected_row['decoded_data'], str):
                            decoded = json.loads(selected_row['decoded_data'])
                        else:
                            decoded = selected_row['decoded_data']
                        
                        # Display in expandable sections
                        with st.expander("📋 Packet Header", expanded=True):
                            if 'header' in decoded:
                                header_df = pd.DataFrame([decoded['header']])
                                st.dataframe(header_df, use_container_width=True)
                            else:
                                st.write("No header information available")
                        
                        with st.expander("🔍 Decoded Values", expanded=True):
                            if 'data' in decoded and decoded['data']:
                                # Display as formatted table
                                data_items = []
                                for key, value in decoded['data'].items():
                                    data_items.append({
                                        'Field': key,
                                        'Value': value,
                                        'Description': get_field_description(key)
                                    })
                                data_df = pd.DataFrame(data_items)
                                st.dataframe(data_df, use_container_width=True)
                            else:
                                st.write("No decoded data available")
                        
                        with st.expander("📄 Raw JSON", expanded=False):
                            st.json(decoded)
                            
                    except json.JSONDecodeError:
                        st.error("Unable to parse decoded data")
                    except Exception as e:
                        st.error(f"Error displaying decoded data: {str(e)}")
                else:
                    st.warning("No decoded data available for this row")



def show_event_monitoring():
    """Event monitoring page"""
    st.header("🔔 Event Monitoring")
    
    tasks = get_tasks()
    if not tasks:
        st.info("No tasks available.")
        return
    
    # Task selection
    task_options = {f"Task #{t['id']}: {t['task_name']}": t['id'] for t in tasks}
    selected_task = st.selectbox("Select Task", list(task_options.keys()))
    task_id = task_options[selected_task]
    
    # Severity filter
    severity_filter = st.multiselect(
        "Filter by severity",
        ["critical", "high", "medium", "low", "info"],
        default=["critical", "high"]
    )
    
    # Fetch events
    if st.button("Load Events", type="primary"):
        with st.spinner("Loading events..."):
            events = get_task_events(task_id, limit=1000)
            
            if not events:
                st.warning("No events found for this task.")
                return
            
            df = pd.DataFrame(events)
            df['event_time'] = pd.to_datetime(df['event_time'])
            
            # Apply filter
            if severity_filter:
                df = df[df['severity'].isin(severity_filter)]
            
            st.success(f"Loaded {len(df)} events")
            
            # Event summary
            st.subheader("Event Summary")
            col1, col2 = st.columns(2)
            
            with col1:
                severity_counts = df['severity'].value_counts()
                fig = px.bar(x=severity_counts.index, y=severity_counts.values,
                            labels={'x': 'Severity', 'y': 'Count'},
                            title='Events by Severity')
                st.plotly_chart(fig, use_container_width=True)
            
            with col2:
                type_counts = df['event_type'].value_counts().head(10)
                fig = px.bar(x=type_counts.index, y=type_counts.values,
                            labels={'x': 'Event Type', 'y': 'Count'},
                            title='Top 10 Event Types')
                st.plotly_chart(fig, use_container_width=True)
            
            # Event timeline
            st.subheader("Event Timeline")
            df_timeline = df.groupby([df['event_time'].dt.date, 'severity']).size().reset_index(name='count')
            fig = px.line(df_timeline, x='event_time', y='count', color='severity',
                         title='Events Over Time by Severity')
            st.plotly_chart(fig, use_container_width=True)
            
            # Recent events
            st.subheader("Recent Events")
            for _, event in df.sort_values('event_time', ascending=False).head(20).iterrows():
                severity_color = {
                    'critical': '🔴',
                    'high': '🟠',
                    'medium': '🟡',
                    'low': '🟢',
                    'info': '🔵'
                }.get(event['severity'], '⚪')
                
                with st.expander(f"{severity_color} {event['event_type']} - {event['event_time']}"):
                    st.write(f"**Message:** {event['message']}")
                    if event.get('details'):
                        st.write(f"**Details:** {event['details']}")


def show_reports():
    """Reports page"""
    st.header("📄 Reports")
    
    tasks = get_tasks()
    if not tasks:
        st.info("No tasks available.")
        return
    
    # Task selection
    task_options = {f"Task #{t['id']}: {t['task_name']}": t['id'] for t in tasks}
    selected_task = st.selectbox("Select Task", list(task_options.keys()))
    task_id = task_options[selected_task]
    
    # Report options
    col1, col2 = st.columns(2)
    with col1:
        report_type = st.selectbox("Report Type", ["summary", "detailed", "analysis"])
    with col2:
        report_format = st.selectbox("Format", ["pdf", "excel", "html"])
    
    include_charts = st.checkbox("Include Charts", value=True)
    
    # Generate report
    if st.button("Generate Report", type="primary"):
        with st.spinner("Generating report..."):
            try:
                payload = {
                    "task_id": task_id,
                    "report_type": report_type,
                    "format": report_format,
                    "include_charts": include_charts
                }
                response = requests.post(f"{API_BASE_URL}/reports/generate", json=payload)
                response.raise_for_status()
                
                result = response.json()
                st.success("✅ Report generated successfully!")
                st.json(result)
                
                if result.get('download_url'):
                    st.info(f"Download URL: {result['download_url']}")
            
            except Exception as e:
                st.error(f"❌ Report generation failed: {str(e)}")
    
    st.divider()
    
    # Task summary report
    st.subheader("Quick Summary")
    if st.button("View Task Summary"):
        with st.spinner("Loading summary..."):
            summary = get_task_summary(task_id)
            
            if summary:
                st.json(summary)
            else:
                st.error("Failed to load summary")


if __name__ == "__main__":
    main()
